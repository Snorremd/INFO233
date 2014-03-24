package game.entity.types;

/**
 * En Tile er en flis på et brett.
 * Det er flere typer Tiles som er mulige, blant annet dynamiske.
 * Dette er fellesgrunnlaget for dem alle.
 * 
 * @author Haakon Løtveit (haakon.lotveit@student.uib.no)
 *
 */
public interface Tile extends Paintable {
	/**
	 * Om en spiller dør av å gå på denne tilen.
	 * @return true hvis spilleren dør, false hvis ikke.
	 */
	public boolean isLethal();
	/**
	 * Om en spiller kan trå på denne tilen.
	 * @return true hvis spilleren kan, false hvis ikke.
	 */
	public boolean isWalkable();
	
	public int getRow();
	public int getColumn();
}
