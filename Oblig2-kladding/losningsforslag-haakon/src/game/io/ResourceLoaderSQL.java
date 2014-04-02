package game.io;

import game.entity.TileLevel;
import game.entity.tiles.AliasNotRegisteredException;
import game.entity.tiles.TileFactory;
import game.entity.tiles.TileNotRegisteredException;
import game.entity.types.Level;
import game.newstuff.entity.monster.MonsterBuilder;
import game.view.gfx.SpriteLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

public class ResourceLoaderSQL implements ResourceLoader {
	protected Map<String, SpriteLoader> spriteLoaders;
	protected Map<String, File> levelFiles;
	protected Map<Integer, String> levelNames;
	protected Map<String, List<Integer>> highScores;
	protected TileFactory factory;

	/**
	 * Dette er en massiv metode som skriker ut etter refaktorering.
	 * Den leser inn alle CSV-filene og lager databasen på nytt igjen.
	 * @throws SQLException 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void reBuildDataBase() throws SQLException, FileNotFoundException, IOException{
		Map<String, String> tableDefinitions = new HashMap<>(6);
		Set<String> existingTables = new HashSet<>(6);

		String[] rekkefølge = {
				"SPRITESHEETS",
				"STANDARDTILES",
				"ALIASES",
				"LEVELS",
				"MONSTER",
				"MONSTERLEVEL",
				"HIGHSCORE",
		};

		tableDefinitions.put(rekkefølge[0], IOStuff.slurpFile("sql/definition/spritesheets.sql"));
		tableDefinitions.put(rekkefølge[1], IOStuff.slurpFile("sql/definition/standard-tiles.sql"));
		tableDefinitions.put(rekkefølge[2], IOStuff.slurpFile("sql/definition/aliases.sql"));
		tableDefinitions.put(rekkefølge[3], IOStuff.slurpFile("sql/definition/levels.sql"));
		tableDefinitions.put(rekkefølge[4], IOStuff.slurpFile("sql/definition/monster.sql"));
		tableDefinitions.put(rekkefølge[5], IOStuff.slurpFile("sql/definition/monster-level.sql"));
		tableDefinitions.put(rekkefølge[6], IOStuff.slurpFile("sql/definition/highscore.sql"));

		String getTables = IOStuff.slurpFile("sql/defined-tables.sql");

		try(Connection conn = ConnectionHolder.getConnection()){
			/* Finner ut hvilke tabeller som er der fra før av */
			try(PreparedStatement registeredTables = conn.prepareStatement(getTables)){
				try(ResultSet rs = registeredTables.executeQuery()){
					while(rs.next()){
						existingTables.add(rs.getString("tablename"));
					}
				}
			}
			/* Fjern dem i korrekt rekkefølge */
			for(int i = rekkefølge.length - 1; i >= 0; --i){
				String theBase = String.format("DROP TABLE %s", rekkefølge[i]);
				if(existingTables.contains(rekkefølge[i])){
					try(Statement drop = conn.createStatement()){
						drop.execute(theBase);
					}
				}
			}
			/* Lag på nytt i korrekt rekkefølge */
			for(int i = 0; i < rekkefølge.length; ++i){
				try(PreparedStatement create = conn.prepareStatement(tableDefinitions.get(rekkefølge[i]))){
					create.execute();
				}
			}

			/* Sett inn i rekkefølge */
			String insert;

			insert = IOStuff.slurpFile("sql/insert/spritesheets.sql");
			try(CSVReader rdr = new CSVReader(new FileReader(new File("res/spritesheets.csv")), ',','"',1)){
				for(String[] row : rdr.readAll()){
					try(PreparedStatement ps = conn.prepareStatement(insert)){
						ps.setString(1, row[0]);
						ps.setString(2, row[1]);
						ps.setInt(3, Integer.valueOf(row[2]));
						ps.execute();
					}
				}
			}		

			insert = IOStuff.slurpFile("sql/insert/standard-tiles.sql");
			try(CSVReader rdr = new CSVReader(new FileReader(new File("res/standard-tiles.csv")), ',','"',1)){
				for(String[] row : rdr.readAll()){
					try(PreparedStatement ps = conn.prepareStatement(insert)){
						ps.setString(1, row[0]);
						ps.setBoolean(2, Boolean.valueOf(row[1]));
						ps.setBoolean(3, Boolean.valueOf(row[2]));
						ps.setInt(4, Integer.valueOf(row[3]));
						ps.setInt(5, Integer.valueOf(row[4]));
						ps.execute();
					}
				}
			}		

			insert = IOStuff.slurpFile("sql/insert/aliases.sql");
			try(CSVReader rdr = new CSVReader(new FileReader(new File("res/alias.csv")), ',','"',1)){
				for(String[] row : rdr.readAll()){
					try(PreparedStatement ps = conn.prepareStatement(insert)){
						ps.setString(1, row[0]);
						ps.setString(2, row[1]);
						ps.execute();
					}
				}
			}		

			insert = IOStuff.slurpFile("sql/insert/levels.sql");
			try(CSVReader rdr = new CSVReader(new FileReader(new File("res/adventure.csv")), ',','"',1)){
				for(String[] row : rdr.readAll()){
					try(PreparedStatement ps = conn.prepareStatement(insert)){
						ps.setInt(1, Integer.valueOf(row[0]));
						ps.setString(2, row[1]);
						ps.setString(3, row[2]);
						ps.execute();
					}
				}
			}

			insert = IOStuff.slurpFile("sql/insert/monster.sql");
			try(CSVReader rdr = new CSVReader(new FileReader(new File("res/monster.csv")), ',','"',1)){
				for(String[] row : rdr.readAll()){
					try(PreparedStatement ps = conn.prepareStatement(insert)){
						ps.setString(1, row[0]);
						ps.setInt(2, Integer.valueOf(row[1]));
						ps.setString(3, row[2]);
						ps.setInt(4, Integer.valueOf(row[3]));
						ps.execute();
					}
				}
			}

			insert = IOStuff.slurpFile("sql/insert/monster-level.sql");
			try(CSVReader rdr = new CSVReader(new FileReader(new File("res/monster-level.csv")), ',','"',1)){
				for(String[] row : rdr.readAll()){
					try(PreparedStatement ps = conn.prepareStatement(insert)){
						ps.setString(1, row[0]);
						ps.setString(2, row[1]);
						ps.setInt(3, Integer.valueOf(row[2]));
						ps.setInt(4, Integer.valueOf(row[3]));
						ps.setString(5, row[4]);
						ps.execute();
					}
				}
			}
		}

	}

	public ResourceLoaderSQL() throws SQLException, IOException, TileNotRegisteredException {
		String sql;
		try(Connection conn = ConnectionHolder.getConnection()){
			try(Statement query = conn.createStatement()){

				/* Grab the spritesheets from the db and make spriteloaders */
				sql = IOStuff.slurpFile("sql/select/spritesheets.sql");
				this.spriteLoaders = new HashMap<>();
				try(ResultSet rs = query.executeQuery(sql)){
					while(rs.next()){
						String name = rs.getString("sheetname");
						File imageFile = new File(rs.getString("location"));
						int tilesize = rs.getInt("tilesize");
						spriteLoaders.put(name, new SpriteLoader(imageFile, tilesize));
						System.out.printf("[INFO] SpriteLoader(%d) \"%s\" loaded, with file \"%s\"%n", tilesize, name, imageFile.getAbsolutePath());
					}
				}

				/* Grab the levels */
				levelFiles = new HashMap<>();
				levelNames = new HashMap<>();
				sql = IOStuff.slurpFile("sql/select/levels.sql");
				try(ResultSet rs = query.executeQuery(sql)){
					int level = 1; /* Forsikrer oss om at brettene går fra 1-n */
					while(rs.next()){
						String name = rs.getString("levelname");
						File path = new File(String.format("res/levels/%s", rs.getString("file")));
						levelFiles.put(name, path);
						levelNames.put(level, name);

						System.out.printf("[INFO] Registered level %d (%s) %s%n", level, name, path.getAbsolutePath());
						level++;
					}
				}

				/* Get all the highscores */
				highScores = new HashMap<>();
				sql = IOStuff.slurpFile("sql/select/highscore.sql");
				try(PreparedStatement ps = conn.prepareStatement(sql)){
					for(String levelName : levelNames.values()){
						ps.setString(1, levelName);
						List<Integer> highScores = new LinkedList<>();
						try(ResultSet rs = ps.executeQuery()){
							while(rs.next()){
								highScores.add(rs.getInt("tenthsOfSeconds"));
							}
						}
						this.highScores.put(levelName, highScores);
					}
				}
				
				/* Make the tilefactory */
				try {
					factory = new TileFactory(this.getSpriteLoader("tiles"));
				} catch (SpriteSheetNotFoundException e) {
					throw new IOException("res/spritesheets.csv does not contain a definition for a spritesheet called \"tiles\".");
				}

				/* Register the tiles and aliases */
				sql = IOStuff.slurpFile("sql/select/standard-tiles.sql");
				try(ResultSet rs = query.executeQuery(sql)){
					while(rs.next()){
						factory.registerTile(rs.getString("tilename"),
								rs.getBoolean("walkable"),
								rs.getBoolean("lethal"),
								rs.getInt("spriteCol"),
								rs.getInt("spriteRow"));
					}
				}

				sql = IOStuff.slurpFile("sql/select/aliases.sql");
				try(ResultSet rs = query.executeQuery(sql)){
					while(rs.next()){
						factory.registerAlias(rs.getString("letter").charAt(0), rs.getString("tilename"));
					}
				}
			}
		}
	}

	@Override
	public Level getLevel(String name) throws LevelNotFoundException,
	BuildLevelException {
		TileLevel level = null;
		try {
			level = TileLevel.load(factory, levelFiles.get(name));
		} catch (TileNotRegisteredException | AliasNotRegisteredException e) {
			throw new BuildLevelException("Could not build level", e);
		} catch (FileNotFoundException fnfe){
			throw new LevelNotFoundException("Couldn't load levelfile", fnfe);
		}
		try(Connection conn = ConnectionHolder.getConnection()){
			String sql = IOStuff.slurpFile("sql/select/monster-monster-level.sql");
			try(PreparedStatement query = conn.prepareStatement(sql)){
				query.setString(1, name);
				try(ResultSet rs = query.executeQuery()){
					while(rs.next()){
						String monsterName = rs.getString("monstername");
						level.registerMonster(new MonsterBuilder()
											.type(monsterName)
											.level(level)
											.loader(getSpriteLoader(rs.getString("spritesheet")))
											.priority(rs.getByte("priority"))
											.rute(rs.getString("patrol"))
											.column(rs.getInt("monsterCol"))
											.row(rs.getInt("monsterRow"))
											.create());
					}
				} catch (SpriteSheetNotFoundException e) {
					throw new BuildLevelException("Couldn't create monsters for the level", e);
				}
			}
		} catch(SQLException sqle){
			throw new BuildLevelException("Couldn't talk to database", sqle);
		} catch(IOException ioe){
			throw new BuildLevelException("Couldn't read SQL-file", ioe);
		}
		return level;
	}

	@Override
	public Level getLevel(int number) throws LevelNotFoundException,
	BuildLevelException {
		return getLevel(this.levelNames.get(number));
	}

	@Override
	public SpriteLoader getSpriteLoader(String name)
			throws SpriteSheetNotFoundException {
		if(!this.spriteLoaders.containsKey(name)){
			throw new SpriteSheetNotFoundException(String.format("Spritesheet \"%s\" not found.", name));
		}
		return this.spriteLoaders.get(name);
	}

	@Override
	public TileFactory getTileFactory() {
		return this.factory;
	}

	@Override
	public int getNumLevels() {
		return this.levelNames.size();
	}

	@Override
	public List<Integer> getHighScores(String levelName) {
		if(!highScores.containsKey(levelName)){
			return new LinkedList<Integer>();
		}
		else {
			return highScores.get(levelName);
		}
	}

	@Override
	public List<Integer> getHighScores(int level) {
		return this.getHighScores(this.levelNames.get(level));
	}

	@Override
	public void addHighScore(String level, int score) {
		if(!this.highScores.containsKey(level)){
			return;
		}
		File highscoreInsert = new File("sql/insert/highscore.sql");
		List<Integer> levelHighScores = this.highScores.get(level);
		levelHighScores.add(score);
		Collections.sort(levelHighScores);
		
		/* Legger til i databasen */
		try(Connection conn = ConnectionHolder.getConnection()){
			String sql = IOStuff.slurpFile(highscoreInsert);
			try(PreparedStatement insert = conn.prepareStatement(sql)){
				insert.setString(1, level);
				insert.setInt(2, score);
				insert.execute();
			}
		} catch(SQLException e){
			System.out.println("[ERROR] Couldn't save to database");
		} catch (FileNotFoundException e) {
			System.out.printf("[ERROR] Couldn't find SQL-file at %s%n", highscoreInsert.getAbsolutePath());
		}
	}

	@Override
	public void addHighScore(int level, int score) {
		if(!levelNames.containsKey(level)){
			return;
		}
		addHighScore(levelNames.get(level), score);
	}

}
