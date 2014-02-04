/**
 * 
 */
package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Snorre Magnus Davøen
 *
 */
public class Config {

	/**
	 * Read the Twitter oauth credentials from a
	 * properties file. If the file cannot be read,
	 * throw an exception.
	 * 
	 * Code sourced from: http://www.mkyong.com/java/java-properties-file-examples/
	 * 
	 * @return
	 */
	public static Properties getTwitterProperties() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config/twitterauth.properties");
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Could not load properties!");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("Could not close input stream!");
				}
			}
		}

		return prop;

	}

}
