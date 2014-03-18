package game.entity;

import game.entity.tiles.IllegalTileException;
import game.entity.types.Level;
import game.entity.types.Tile;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TileLevel implements Level {
	private int startCol, startRow, goalCol, goalRow;

	public static TileLevel load(TileFactory dispenser, File levelFile) throws FileNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException {
		int numCols, numRows, startCol, startRow, goalCol, goalRow;
		String[] wholeMap;
		/* Dere som har tatt inf100 kjenner forhåpentligvis igjen java.util.Scanner.
		 * Scanner er en finfin sak som kan lese fra filer så vel som fra tastaturet.
		 */
		try(Scanner mapreader = new Scanner(levelFile)){
			try(Scanner firstLine = new Scanner(mapreader.nextLine())){
				
				numCols = firstLine.nextInt();
				numRows = firstLine.nextInt();
				startCol = firstLine.nextInt();
				startRow = firstLine.nextInt();
				goalCol = firstLine.nextInt();
				goalRow = firstLine.nextInt();
				
				wholeMap = mapreader.useDelimiter("\\Z").next().split("\n"); 
				/*
				 * \\Z blir til \Z, som blir til EOF-tegnet (End Of File), på samme måte som "\n" blir til newline (ny linje).
				 * Det vi sier ovenfor er altså at scanneren skal lese til slutten av filen, og så sier vi at den skal lese neste.
				 * Vi kan bruke hva som helst som delimiter, også ting som "fisk".
				 * Hadde vi hatt strengen "eplefiskpærefiskappelsin" kunne vi brukt en scanner med "fisk" som delimiter til å hente ut "eple", "pære" og "appelsin".
				 * Dette er et veldig fint triks hvis du bare vil ha en hel fil som en streng:
				 * String fil = "";
				 * try(Scanner sc = new Scanner(hva enn filen heter)){
				 *     fil = sc.useDelimiter("\\Z").next();
				 * } catch (FileNotFoundException fnfe){
				 *     // gjør ting om filen ikke blir funnet
				 * }
				 */
			}
		}
		
		Tile[][] map = new Tile[numCols][numRows];
		
		for(int curRow = 0; curRow < numRows; ++curRow){
			String row = wholeMap[curRow];
			String[] columns = row.split("\\s+");
			
			for(int curCol = 0; curCol < numCols; ++curCol){
				map[curCol][curRow] = dispenser.make(columns[curCol].charAt(0), curCol, curRow);
			}
		}
		TileLevel tl = new TileLevel(numCols, numRows, dispenser.tilesize(), startCol, startRow, goalCol, goalRow, map);
		return tl;
	}
	
	int width, height, tilesize;
	Tile[][] map; // map as in geography, not data structure.
	// TODO: legg til andre aktører enn spilleren
	
	public TileLevel(int width, int height, int tilesize, int startCol, int startRow, int goalCol, int goalRow, Tile[][] map){
		this.startCol = startCol;
		this.startRow = startRow;
		this.goalCol = goalCol;
		this.goalRow = goalRow;
		this.width = width;
		this.height = height;
		this.tilesize = tilesize;
		this.map = map;
	}
	
	@Override
	public void render(Graphics gfx) {
		for(Tile[] tiles : map){
			for(Tile t : tiles){
				t.render(gfx);
			}
		}
	}

	@Override
	public void tick() {
		// Når det kommer monstre skal de tickes her.
	}

	@Override
	public boolean walkable(int column, int row) {
		boolean canWalk = checkLevelBounds(column, row);
		
		if(!canWalk) return false;
		
		canWalk = map[column][row].isWalkable();

		return canWalk;
	}
	
	protected boolean checkLevelBounds(int column, int row){
		return !(column < 0 || column > width - 1 || row < 0 || row > height - 1);
	}

	@Override
	public Tile tileAt(int column, int row) {
		// greylevel har ikke noen tiles, så den returnerer null
		return null;
	}

	@Override
	public int tileRows() {
		return map.length;
	}

	@Override
	public int tileColumns() {
		return map.length > 0? map[0].length : 0;
	}

	@Override
	public int tilesize() {
		return tilesize;
	}

	@Override
	public int getStartingColumn() {
		return startCol;
	}

	@Override
	public int getStartingRow() {
		return startRow;
	}
	

	@Override 
	public String toString(){
		return String.format("TileMap (%d×%d), Start: (%d,%d), Goal: (%d,%d)",
							 tileColumns(), tileRows(), getStartingColumn(), getStartingRow(), goalCol, goalRow);
	}

	@Override
	public int getGoalColumn() {
		return goalCol;
	}

	@Override
	public int getGoalRow() {
		return goalRow;
	}

}
