package game.entity;

import game.entity.tiles.IllegalTileException;
import game.entity.tiles.TileBuilder;
import game.entity.types.Tile;
import game.gfx.SpriteLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

public class TileFactory {
	private SpriteLoader sprites;

	/*
	 * Dette ser ekkelt ut, men alternativet er vel en Map<String, TileConfiguration>
	 * der TileConfiguration har en metode ala create() : Tile eller noe
	 * Er ikke helt sikker på beste måten å ha dette på. En database kanskje?
	 */
	private Set<String> names;
	private Map<String, Boolean> walkable;
	private Map<String, Boolean> pushable;
	private Map<String, Boolean> lethal;
	private Map<String, Integer> column; // kolonne i spritesheet, ikke plassering
	private Map<String, Integer> row;    // også per spritesheet. 
	private Map<Character, String>  alias;

	private void init(){
		names    = new HashSet<>();
		walkable = new HashMap<>();
		pushable = new HashMap<>();
		lethal   = new HashMap<>();
		column   = new HashMap<>();
		row      = new HashMap<>();
		alias    = new HashMap<>();
	}
	
	/**
	 * Lager en ny tilefactory, basert på en spesifikk spriteloader.
	 * @param sprites spriteloaderen som skal brukes.
	 */
	public TileFactory(SpriteLoader sprites){
		this.sprites = sprites;
		init();
	}

	public boolean registerAlias(Character letter, String aliasFor) throws TileNotRegisteredException {
		if(!names.contains(aliasFor)) throw new TileNotRegisteredException("Cannot alias a tile that does not yet exist");		
		
		if(alias.containsKey(letter)){
			return false;
		}
			
		System.out.printf("[INFO] Aliasing %s to %s%n", letter, aliasFor);
		alias.put(letter, aliasFor);
		return true;
	}
	
	public boolean registerTile(List<String[]> csvs){
		boolean retval = true;
		
		for(String[] csv : csvs){
			retval &= this.registerTile(csv);
		}
		
		return retval;
	}
	
	public boolean registerTile(String[] csv){
		String  name     = csv[0];
		if(names.contains(name)){
			return false;
		}
		names.add(name);

		this.walkable.put(name, Boolean.parseBoolean(csv[1]));
		this.pushable.put(name, Boolean.parseBoolean(csv[2]));
		this.lethal.put(name, Boolean.parseBoolean(csv[3]));
		this.column.put(name, Integer.parseInt(csv[4]));
		this.row.put(name, Integer.parseInt(csv[5]));

		System.out.printf("[INFO] Registered tile %s%n", name);
		return true;
	}

	/**
	 * Lager en ny (statisk) tile basert på data som er registrert.
	 * @param name Navnet på tilen
	 * @param x x-posisjonen til tilen (ikke piksler, men type rad/kolonner)
	 * @param y y-posisjonen til tilen (ikke piksler, men type rad/kolonner)
	 * @return en ny tile, satt til gitt posisjon.
	 * @throws TileNotRegisteredException dersom ingen tile med det navnet er gitt.
	 * @throws IllegalTileException dersom de registrerte dataene er ulovlige.
	 */
	public Tile make(String name, int x, int y) throws TileNotRegisteredException, IllegalTileException {
		if(!names.contains(name)){
			String error = String.format("Tile \"%s\" is not registered in database. Have you loaded all data correctly?", name);
			throw new TileNotRegisteredException(error);
		}

		/*
		 *  Dette er forhåpentligvis litt letere å holde orden på
		 *  Enn å måtte lese gjennom en lanregisterTile(definition)g ugjennomtrengelig  masse av et kall til new StaticTile
		 */
		return new TileBuilder()
			.col(x)
			.row(y)
			.spriteX(row.get(name))
			.spriteY(column.get(name))
			.spriteloader(sprites)
			.walkable(walkable.get(name))
			.pushable(pushable.get(name))
			.lethal(lethal.get(name))
			.create();
	}

	/**
	 * Lar deg bruke et forhåndsdefinert alias for å danne tiles.
	 * Laget for å lettere lese inn tiles fra brettfiler.
	 * @param letter tegnet som skal brukes.
	 * @param x raden tilen skal inn i
	 * @param y tilen skal inn i
	 * @return en ny Tile, som om du hadde kalt make med en streng.
	 * @throws TileNotRegisteredException om strengen som aliaset er definert for ikke er funnet
	 * @throws IllegalTileException om de registrerte dataene er ulovlige
	 * @throws AliasNotRegisteredException om du ikke har registrert aliaset.
	 */
	public Tile make(Character letter, int x, int y) throws TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException {
		if(!alias.containsKey(letter)){
			String error = String.format("Alias \"%c\" is not defined. Have you loaded all data?", letter);
			throw new AliasNotRegisteredException(error);
		}
		return make(alias.get(letter), x, y);
	}

	public boolean registerAliases(File aliasPairs) throws TileNotRegisteredException, FileNotFoundException, IOException {
		boolean success = true;
		try(CSVReader rdr = new CSVReader(new FileReader(aliasPairs), ',', '"', 1)){
			for(String[] line : rdr.readAll()){
				if(line.length < 2){
					System.err.printf("[ERROR] row has less than two elements. Skipping%n");
					continue;
				}
				if(line[0].length() != 1){
					System.err.printf("[ERROR] %s is not a single character. Skipping.%n", line[0]);
					continue;
				}
				
				Character c = line[0].charAt(0);
				String name = line[1];
				
				success &= registerAlias(c, name);
				
			}
		}
		
		return success;
		
	}

	public boolean registerTiles(File csv) {
		try(CSVReader reader = new CSVReader(new FileReader(csv), ',', '"', 1)){
			this.registerTile(reader.readAll());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public int tilesize() {
		return sprites.tilesize();
	}
}
