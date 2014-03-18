package game.util;

public class Mover {
	public static final int COLUMN = 0;
	public static final int ROW = 1;
	
	public static int[] position(Direction dir, int x, int y){
		int[] retval = {x, y};
		
		switch(dir){
		case NORTH:
			--retval[1]; break;
		case EAST:
			--retval[0]; break;
		case WEST:
			++retval[0]; break;
		case SOUTH:
			++retval[1]; break;
		}
		
		return retval;
	}
}
