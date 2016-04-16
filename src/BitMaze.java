import java.awt.Dimension;


public class BitMaze {
	public static final String TITLE = "BitMaze";
	public static final Dimension DIMENSION = new Dimension(300, 300);
	public static final String ICON_PATH = "assets/icon.png"; 
	public static final int TARGET_FPS = 60;
	public static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
	public static BitWindow bitWindow;
	
	private Maze maze;
	
	public BitMaze() {
		bitWindow = new BitWindow(TITLE, DIMENSION, ICON_PATH);
		maze = new Maze();
		
		run();
	}
	
	public void run() {
		long lastLoopTime = System.nanoTime();
		
		while(true) {
			long now = System.nanoTime();
			double updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / BitMaze.OPTIMAL_TIME;
			
			// Update
			maze.update(delta);
			
			// Paint
			bitWindow.repaint();
			
			try {
				long value = (lastLoopTime - System.nanoTime() + BitMaze.OPTIMAL_TIME) / 1000000;
				if(value > 0) {
					Thread.sleep(value);					
				}
				else {
					Thread.sleep(5);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static void main(String[] args) {
		new BitMaze();
	}
}
