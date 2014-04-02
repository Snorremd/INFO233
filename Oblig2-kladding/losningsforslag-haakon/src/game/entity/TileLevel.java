package game.entity;

import static game.util.EffectiveJavaHasher.hashInteger;
import game.entity.monster.PatruljeMonster;
import game.entity.tiles.AliasNotRegisteredException;
import game.entity.tiles.TileFactory;
import game.entity.tiles.TileNotRegisteredException;
import game.entity.types.Level;
import game.entity.types.Monster;
import game.entity.types.Player;
import game.entity.types.Tile;
import game.util.Direction;
import game.view.gfx.SpriteLoader;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * En standard implementasjon av et todimensjonalt brett.
 * Den bruker tiles (fliser) til å male brettet.
 * For å skaffe flisene bruker den en {@link TileFactory}.
 * 
 * @author Haakon Løtveit (haakon.lotveit@student.uib.no)
 *
 */
public class TileLevel implements Level {
	private int startCol, startRow, goalCol, goalRow;

	/**
	 * Leser et brett fra en fil.
	 * @param dispenser fabrikken som skal spytte ut flisene
	 * @param levelFile brettfilen
	 * @return et ferdig TileLevel, klart til bruk.
	 * @throws FileNotFoundException dersom filen ikke blir funnet.
	 * @throws TileNotRegisteredException Dersom en Tile/flis blir referert til som ikke er registrert i dispenser.
	 * @throws AliasNotRegisteredException dersom et alias blir referert til som ikke er registrert i dispenser.
	 */
	public static TileLevel load(TileFactory dispenser, File levelFile) throws FileNotFoundException, TileNotRegisteredException, AliasNotRegisteredException {
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

				/* Se game.util.IOStuff for mer detaljer*/
				wholeMap = mapreader.useDelimiter("\\Z").next().split("\n"); 

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
		return new TileLevel(numCols, numRows, dispenser.tilesize(), startCol, startRow, goalCol, goalRow, map);
	}

	private int width, height, tilesize;
	private Tile[][] map; /* map som i geografien på brettet, ikke datastrukturen. */
	private PriorityQueue<Monster> activeQueue;
	private PriorityQueue<Monster> fillingQueue;
	private List<Monster> allMonsters;
	private List<Monster> renderList;
	/**
	 * Standard konstruktør for et brett.
	 * Det er kanskje ikke så veldig relevant å bruke denne, sannsynligvis kommer dere til å holde dere Til TileLevel.loadLevel(TileFactory,File)
	 * @param width antall kolonner.
	 * @param height antall rader
	 * @param tilesize størrelsen på en flis.
	 * @param startCol hvilken kolonne spilleren skal begynne på kartet på.
	 * @param startRow hvilken rad spilleren skal begynne på kartet på.
	 * @param goalCol hvilken kolonne spilleren skal ende opp på.
	 * @param goalRow hvilken rad spilleren skal ende opp på.
	 * @param map en tabell av fliser som er kartet. Det er antatt, men ikke sjekket at tabellen er firkantet.
	 */
	public TileLevel(int width, int height, int tilesize, int startCol, int startRow, int goalCol, int goalRow, Tile[][] map){
		this.startCol = startCol;
		this.startRow = startRow;
		this.goalCol = goalCol;
		this.goalRow = goalRow;
		this.width = width;
		this.height = height;
		this.tilesize = tilesize;
		this.map = map;
		this.activeQueue  = new PriorityQueue<>(6, MonsterPrioritizer.INSTANCE); // Singleton pattern
		this.fillingQueue = new PriorityQueue<>(6, MonsterPrioritizer.INSTANCE);
		this.allMonsters = new LinkedList<>();
		this.renderList = new LinkedList<>();
	}

	@Override
	public void reset(Player player){
		for(Monster m : allMonsters){
			m.reset();
		}
		
		player.setPosition(getStartingColumn(), getStartingRow());
		player.setDirection(Direction.SOUTH);
	}

	@Override
	public void registerMonster(Monster m){
		this.fillingQueue.add(m);
		this.renderList.add(m);
		this.allMonsters.add(m);
		
		System.out.printf("[INFO] Registered monster %s%n", m);
	}


	@Override
	public void render(Graphics gfx) {
		for(Tile[] tiles : map){
			for(Tile t : tiles){
				t.render(gfx);
			}
		}

		for(Monster m : renderList){
			m.render(gfx);
		}
	}

	@Override
	public void tick() {
		while(!activeQueue.isEmpty()){
			Monster doer = activeQueue.poll();
			doer.tick();
			fillingQueue.add(doer);
		}
		
		/* Bytt om køene */
		PriorityQueue<Monster> tmp = activeQueue;
		activeQueue = fillingQueue;
		fillingQueue = tmp;
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

	/**
	 * Enkel metode som sjekker om en posisjon er innenfor brettet.
	 * @param column kolonnen vi sjekker
	 * @param row raden vi sjekker
	 * @return true hvis vi er innenfor bretttet, ellers false
	 */
	protected boolean checkLevelBounds(int column, int row){
		return !(column < 0 || column > width - 1 || row < 0 || row > height - 1);
	}

	@Override
	public Tile tileAt(int column, int row) {
		return checkLevelBounds(column, row)? map[column][row] : null;
	}

	@Override
	public int tileRows() {
		return this.height;
	}

	@Override
	public int tileColumns() {
		return this.width;
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
		boolean deadly = false;
		for(Monster m : allMonsters){
			if(m.getColumn() == column && m.getRow() == row){
				deadly = true;
			}
		}
		if(deadly) return deadly;
		return  !checkLevelBounds(column, row) ||
				tileAt(column, row).isLethal();
				
	}

	@Override
	public boolean equals(Object obj){
		if(null == obj) return false;
		if(obj instanceof TileLevel){
			TileLevel tl = (TileLevel) obj;
			return Arrays.deepEquals(tl.map, this.map)      &&
					tl.allMonsters.equals(this.allMonsters) && 
					tl.height   == this.height              &&
					tl.width    == this.width               &&
					tl.startCol == this.startCol            &&
					tl.goalCol  == this.goalCol             &&
					tl.startRow == this.startRow            &&
					tl.goalRow  == this.goalRow;

		}
		return false;
	}

	@Override
	public int hashCode(){
		int hash = 17851; /* Et primtall fra en liste som ble funnet vha. google */
		hash = hash * 31 + allMonsters.hashCode(); 
		int mapHash = 19;
		for(Tile[] row : map){
			mapHash = mapHash * 31 + Arrays.hashCode(row);
		}
		hash = hash * 31 + mapHash;
		hash = hash * 31 + hashInteger(height);
		hash = hash * 31 + hashInteger(width);
		hash = hash * 31 + hashInteger(startCol);
		hash = hash * 31 + hashInteger(goalCol);
		hash = hash * 31 + hashInteger(startRow);
		hash = hash * 31 + hashInteger(goalRow);
		return hash * 31;		
	}

	@Override
	public void removeMonsters() {
		this.renderList = new LinkedList<>();
		this.allMonsters = new LinkedList<>();
		this.activeQueue = new PriorityQueue<>();
		this.fillingQueue = new PriorityQueue<>();
	}
}
