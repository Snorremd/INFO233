package game.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOStuff {

	public static String slurpFile(File file) throws FileNotFoundException{
		try(Scanner slurper = new Scanner(file)){
			return slurper.useDelimiter("\\Z").next();
		}
	}
	
	
}
