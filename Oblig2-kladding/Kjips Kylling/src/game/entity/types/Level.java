package game.entity.types;



public interface Level extends Paintable, Tickable {
	public boolean walkable(int column, int row);
	public Tile tileAt(int column, int row);
	public int tileRows();
	public int tileColumns();
	public int tilesize();
	public int getStartingColumn();
	public int getStartingRow();
}
