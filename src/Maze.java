import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Maze extends KeyAdapter {
	public static final int MAZE_WIDTH = 4;
	public static final int MAZE_HEIGHT = 4;
	public static final float CHANGE_DIRECTION_ANIMATION = 3.0f;
	public static final int NUMBER_FLASHES = 10;
	public static final float FLASH_ANIMATION = 2.0f;
	
	Random rand = new Random();
	
	private boolean[][] horizontalMaze;
	private boolean[][] verticalMaze;
	private int[][] mazeValue;
	private Player player;
	
	private float timer;
	private boolean flash;
	private int countFlash;
	
	public Maze() {
		BitMaze.bitWindow.addKeyListener(this);
		generateNewMaze();
	}
	
	public void generateNewMaze() {
		player = new Player();
		horizontalMaze = new boolean[MAZE_WIDTH][MAZE_HEIGHT + 1];
		verticalMaze = new boolean[MAZE_WIDTH + 1][MAZE_HEIGHT];
		mazeValue = new int[MAZE_WIDTH][MAZE_HEIGHT];
		
		for(int j = 0; j < MAZE_HEIGHT + 1; j++) {
			for(int i = 0; i < MAZE_WIDTH + 1; i++) {
				if(i < MAZE_WIDTH && j < MAZE_HEIGHT) {
					mazeValue[i][j] = j * MAZE_WIDTH + i; 
				}
				if(i < MAZE_WIDTH) {
					horizontalMaze[i][j] = true;
				}
				if(j < MAZE_HEIGHT) {
					verticalMaze[i][j] = true;
				}
			}
		}
		
		for(int i = 0; i < MAZE_WIDTH * MAZE_HEIGHT - 1; i++) {
			boolean next = false;
			while(!next) {
				if(rand.nextBoolean()) {
					// Horizontal
					int x = rand.nextInt(MAZE_WIDTH);
					int y = rand.nextInt(MAZE_HEIGHT - 1) + 1;
					
					if(horizontalMaze[x][y] && mazeValue[x][y-1] != mazeValue[x][y]) {
						next = true;
						horizontalMaze[x][y] = false;
						resolveMazeValue();
					}
				}
				else {
					// Vertical
					int x = rand.nextInt(MAZE_WIDTH - 1) + 1;
					int y = rand.nextInt(MAZE_HEIGHT);
					
					if(verticalMaze[x][y] && mazeValue[x-1][y] != mazeValue[x][y]) {
						next = true;
						verticalMaze[x][y] = false;
						resolveMazeValue();
					}
				}
			}	
		}
		
		int x, y;
		if(rand.nextBoolean()) {
			// Open a horizontal wall output
			x = rand.nextInt(MAZE_WIDTH);
			if(rand.nextBoolean()) {
				// Top
				y = 0;
			}
			else {
				// Bottom
				y = MAZE_HEIGHT;
			}
			horizontalMaze[x][y] = false;
		}
		else {
			// Open a vertical wall output
			y = rand.nextInt(MAZE_HEIGHT);
			if(rand.nextBoolean()) {
				// Left
				x = 0;
			}
			else {
				// Right
				x = MAZE_WIDTH;
			}
			verticalMaze[x][y] = false;
		}
		
		updateView();
	}
	
	public void resolveMazeValue() {
		boolean finish = false;
		while(!finish) {
			finish = true;
			for(int i = 0; i < MAZE_WIDTH; i++) {
				for(int j = 0; j < MAZE_HEIGHT; j++) {
					if(i != 0 || j != 0) {
						if(i > 0) {
							if(!verticalMaze[i][j]) {
								if(mazeValue[i][j] > mazeValue[i - 1][j]) {
									finish = false;
									mazeValue[i][j] = mazeValue[i - 1][j];
								}
								
								if(mazeValue[i][j] < mazeValue[i - 1][j]) {
									finish = false;
									mazeValue[i - 1][j] = mazeValue[i][j];
								}
							}
						}
						if(j > 0) {
							if(!horizontalMaze[i][j]) {
								if(mazeValue[i][j] > mazeValue[i][j - 1]) {
									finish = false;
									mazeValue[i][j] = mazeValue[i][j - 1];
								}
								
								if(mazeValue[i][j] < mazeValue[i][j - 1]) {
									finish = false;
									mazeValue[i][j - 1] = mazeValue[i][j];
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void update(double delta) {
		if(timer > 0) {
			timer -= delta;
			
			if(timer <= 0) {
				timer = 0;
				
				if(flash) {
					countFlash--;
					if(countFlash <= 0) {
						flash = false;
					}
					else {
						timer = FLASH_ANIMATION;
						BitMaze.bitWindow.setOn(!BitMaze.bitWindow.isOn());
						return;
					}
				}
				
				updateView();
			}
		}
	}
	
	private void updateView() {
		if(player.direction == Direction.WEST) {
			BitMaze.bitWindow.setOn(!verticalMaze[player.x][player.y]);
		}
		else if(player.direction == Direction.EAST) {
			BitMaze.bitWindow.setOn(!verticalMaze[player.x+1][player.y]);
		}
		else if(player.direction == Direction.NORTH) {
			BitMaze.bitWindow.setOn(!horizontalMaze[player.x][player.y]);
		}
		else {
			BitMaze.bitWindow.setOn(!horizontalMaze[player.x][player.y+1]);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean change = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			change = true;
			if(player.direction == Direction.WEST) {
				player.direction = Direction.SOUTH;
			}
			else if(player.direction == Direction.EAST) {
				player.direction = Direction.NORTH;
			}
			else if(player.direction == Direction.NORTH) {
				player.direction = Direction.WEST;
			}
			else {
				player.direction = Direction.EAST;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			change = true;
			if(player.direction == Direction.WEST) {
				player.direction = Direction.NORTH;
			}
			else if(player.direction == Direction.EAST) {
				player.direction = Direction.SOUTH;
			}
			else if(player.direction == Direction.NORTH) {
				player.direction = Direction.EAST;
			}
			else {
				player.direction = Direction.WEST;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(player.direction == Direction.WEST && !verticalMaze[player.x][player.y]) {
				change = true;
				player.x--;
			}
			else if(player.direction == Direction.EAST && !verticalMaze[player.x+1][player.y]) {
				change = true;
				player.x++;
			}
			else if(player.direction == Direction.NORTH && !horizontalMaze[player.x][player.y]) {
				change = true;
				player.y--;
			}
			else if(player.direction == Direction.SOUTH && !horizontalMaze[player.x][player.y+1]) {
				change = true;
				player.y++;
			}
			if(player.x < 0 || player.x >= MAZE_WIDTH || player.y < 0 || player.y >= MAZE_HEIGHT) {
				timer = FLASH_ANIMATION;
				flash = true;
				countFlash = NUMBER_FLASHES;
				generateNewMaze();
				change = false;
			}
		}
		
		if(change) {
			timer = CHANGE_DIRECTION_ANIMATION;
			BitMaze.bitWindow.setOn(!BitMaze.bitWindow.isOn());
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int j = 0; j < MAZE_HEIGHT + 1; j++) {
			for(int i = 0; i < MAZE_WIDTH + 1; i++) {
				if(i != 0) {
					if(i - 1 < MAZE_WIDTH) {
						if(horizontalMaze[i - 1][j]) {
							s += "_";
						}
						else {
							s += " ";
						}
					}
					else {
						s += " ";
					}
				}
				else {
					s += " ";
				}
				
				if(j != 0) {
					if(j - 1 < MAZE_HEIGHT) {
						if(verticalMaze[i][j - 1]) {
							s += "|";
						}
						else {
							s += " ";
						}
					}
					else {
						s += " ";
					}
				}
				else {
					s += " ";
				}
			}
			s += "\n";
		}
		return s;
	}
}
