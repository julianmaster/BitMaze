import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public enum Direction {
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RAND = new Random();
	
	public static Direction rand() {
		return VALUES.get(RAND.nextInt(SIZE));
	}
}
