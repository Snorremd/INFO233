package game.entity;

import game.entity.tiles.IllegalTileException;
import game.entity.types.Level;
import game.entity.types.Monster;
import game.entity.types.Player;
import game.entity.types.Tile;
import game.gfx.SpriteLoader;
import game.monster.ExampleMonster;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	Monster theMonster; /* TODO: en del av obligen er å legge til støtte for monstre. Hvordan dere gjør det er opp til dere. Kanskje en datastruktur som ble gjennomgått på forelesning? */
	
	public TileLevel(int width, int height, int tilesize, int startCol, int startRow, int goalCol, int goalRow, Tile[][] map){
		this.startCol = startCol;
		this.startRow = startRow;
		this.goalCol = goalCol;
		this.goalRow = goalRow;
		this.width = width;
		this.height = height;
		this.tilesize = tilesize;
		this.map = map;
		
		/* 
		 * TODO: Dette må selvsagt vekk og erstattes med en ordentlig måte å populere et kart med monstre på.
		 * Hvordan dere velger å lagre hvor monstre er på et brett er selvsagt helt opp til dere. 
		 */
		putMonster();
	}
	
	/* DETTE ER IKKE EN GOD PROSEDYRE. DETTE ER EN STYGG HACK FOR Å VISE ET ENKELT MONSTER. */
	private void putMonster(){
		for(int row = 0; row < height; ++row){
			for(int col = 0; col < width; ++col){
				if(col == startCol && row == startRow){
					continue; /* Hopp over der spilleren starter. */
				}
				if(tileAt(col, row).isWalkable()){
					try {
						theMonster = new ExampleMonster(this, new SpriteLoader(new File("art/monstre.png"), 64), col, row);
						System.out.printf("[HACK] Put a monster, at (%d,%d)%n", col, row);
						return;
					} catch (IOException e) {
						/* Siden dere skal bytte dette ut med noe mer fornuftig, står dette her uten å gjøre noe fornuftig. */
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void registerMonster(Monster m){
		this.theMonster = m;
	}
	
	@Override
	public void render(Graphics gfx) {
		for(Tile[] tiles : map){
			for(Tile t : tiles){
				t.render(gfx);
			}
		}
		
		/* TODO: Dette vil jo selvsagt ikke virke når du skal ha flere monstre. */
		theMonster.render(gfx);
	}

	@Override
	public void tick() {
		/* TODO: Du må selvsagt bytte dette ut med en måte å håndtere monstre på. */
		theMonster.tick();
	}

	@Override
	public boolean walkable(int column, int row) {
		if(!(checkLevelBounds(column, row))){
			return false;		
		}
		if(!(map[column][row].isWalkable())){
			return false;
		}

		return true;
	}
	
	protected boolean checkLevelBounds(int column, int row){
		return !(column < 0 || column > width - 1 || row < 0 || row > height - 1);
	}

	@Override
	public Tile tileAt(int column, int row) {
		return checkLevelBounds(column, row)? map[column][row] : null;
	}

	@Override
	public int tileRows() {
		return map.length;
	}

	@Override
	public int tileColumns() {
		/* antagelsen her er at kartet er firkantet. Det er også antatt i loadFromFile-metoden. */
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

	@Override
	public boolean isPlaceDeadly(int column, int row) {
			return  (!checkLevelBounds(column, row) ||
					 tileAt(column, row).isLethal() ||
				 	 theMonster.getColumn() == column && theMonster.getRow() == row);
	}

}
