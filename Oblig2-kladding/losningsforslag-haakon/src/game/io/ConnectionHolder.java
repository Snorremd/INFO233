package game.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHolder {
	private static Connection connection = null;
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";     /* Hvilken type database vi vil ha */
	private static String protocol = "jdbc:derby:"; 				 	       /* Hvordan vi vil snakke med Derby. */
	private static String databaseNavn = "sql-db";	    					   /* Hva databasen vil hete. (En mappe med filer i vil bli lagd. */
	private static String jdbcUrl = protocol + databaseNavn + ";create=true";  /* URLen spesifiserer til DriverManageren vår alt den trenger å vite for å koble oss på Derby */
	
	static{ /* Blir kjørt når klassen blir lastet. */
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.err.println("Couldn't load databases. Dying.");
			System.exit(1);
		}                  
	}
	
	public static Connection getConnection() throws SQLException {
		if(null == connection || connection.isClosed()){
			connection = DriverManager.getConnection(jdbcUrl);
		}
		return connection;
	}

	public static void shutDown() throws SQLException {
		try{
			DriverManager.getConnection(protocol + databaseNavn +";shutdown=true");
		} catch(SQLException sqle){
			int shutDownCode = 45_000;
			if(!(sqle.getErrorCode() == shutDownCode)){
				throw sqle;
			}
		}
		
	}

}
