package game.util;

/**
 * En hjelpeklasse for å flytte ting rundt på brettet.
 * @author Haakon Løtveit (haakon.lotveit@student.uib.no)
 *
 */
public class Mover {
	/**
	 * Gitt at du kaller Mover.position(int,int)[Mover.COLUMN], vil du få ut kolonnen i arrayet.
	 * Satt til 0.
	 */
	public static final int COLUMN = 0;
	
	/**
	 * Gitt at du kaller Mover.position(int,int)[Mover.ROW], vil du få ut raden i arrayet.
	 * Satt til 1.
	 */
	public static final int ROW = 1;
	
	/**
	 * Beregner hvor du ender opp hvis du går et skritt i en gitt retning.
	 * @param dir Retningen du går i.
	 * @param x kolonnen du er i.
	 * @param y raden du er i.
	 * @return en int[] med lengde 2. index 0 representerer kolonnen, og index 1 representerer raden.
	 */
	public static int[] position(Direction dir, int x, int y){
		int[] retval = {x, y};
		
		switch(dir){
		case NORTH:
			--retval[ROW]; break;
		case EAST:
			++retval[COLUMN]; break;
		case WEST:
			--retval[COLUMN]; break;
		case SOUTH:
			++retval[ROW]; break;
		}
		
		return retval;
	}
}
