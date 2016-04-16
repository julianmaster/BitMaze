import java.util.Random;


public class Player {
	public int x;
	public int y;
	public Direction direction;
	
	private final Random rand = new Random();
	
	public Player() {
		x = rand.nextInt(Maze.MAZE_WIDTH);
		y = rand.nextInt(Maze.MAZE_HEIGHT);
		direction = Direction.rand();
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "] - " + direction.name();
	}
}
