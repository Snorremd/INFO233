package main;

import game.controller.Game;
import game.io.ConnectionHolder;
import game.io.ResourceLoader;
import game.io.ResourceLoaderCSV;
import game.io.ResourceLoaderSQL;

public class Main {
	
	/**
	 * Dette laster inn en ResourceLoader som kan hente ut brett og slikt.
	 * Deretter lager det et nytt Game-objekt som det kjører start på.
	 * @param args blir ignorert
	 * @throws Throwable Vi kaster alle exceptions. Dette er egentlig en dum ting.
	 */
	public static void main(String[] args) throws Throwable {
		if(args.length > 0){
			boolean exit0 = false;
			String arg = args[0];
			switch(arg){
			case "--rebuild":
				System.out.println("Rebuilding database");
				ResourceLoaderSQL.reBuildDataBase();
				exit0 = true;
				break;
			case "--version":
				System.out.println("Kjips Kylling v0.1 - løsningsforslag Haakon");
				exit0 = true;
				break;
			case "--help":
				displayHelp();
				exit0 = true;
				break;
			case "--dev-run":
				ResourceLoaderSQL.reBuildDataBase();
				exit0 = false;
				break;
			default:
				System.out.printf("Unknown command %s%n", arg);
				displayHelp();
				System.exit(1);
			}
			if(exit0){
				System.exit(0);
			}
		}
		/* 
		 * Når dere er ferdig med ResourceLoaderSQL-klassen, kan dere gjøre:
		 * ResourceLoader loader = new ResourceLoaderSQL() istedenfor.
		 * Det blir den eneste forskjellen for resten av spillet.
		 */
		ResourceLoader loader = new ResourceLoaderSQL(); 
		
		Game game = new Game(loader);
		game.start();
		
		System.out.println("Spill over, avslutter");
		game.shutdown();
		ConnectionHolder.shutDown();
		System.exit(0); // Fordi ordentlig kode for å slå av ting er for vanskelig... :)
	}
	
	private static void displayHelp(){
	    System.out.println("Kjips Kylling: A simple tile-based game reminiscent of Chip's Challenge.");
	    System.out.println("Only the first argument is parsed");
	    System.out.println("  arg\t\t\tmeaning");
	    System.out.println("--rebuild\t\trebuild the database from csv-files and exit. If you mod the game through the CSV-files, this is the command to call.");
	    System.out.println("--version\t\tdisplay the version of the game and exit.");
	    System.out.println("--help\t\t\tdisplay this helpful text");
	    System.out.println("--dev-run\t\trebuild the database and then runs the game. If you are a developer this is probably the one you want Eclipse to run.");
	}
}
