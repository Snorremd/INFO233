package main;

import game.controller.Game;
import game.controller.TickType;
import game.util.ResourceLoader;
import game.util.ResourceLoaderCSV;

public class Main {
	
	/**
	 * Dette er hvor spillet begynner akkurat nå, før vi kan gå fra ett brett til et annet, laster spillet bare opp det første brettet, og så begynner du på det.
	 * @param args blir ignorert
	 * @throws Throwable Vi kaster alle exceptions. Dette er egentlig en dum ting.
	 */
	public static void main(String[] args) throws Throwable {
		ResourceLoader loader = new ResourceLoaderCSV(); 
		new Game(loader).start();
		
	}
}
