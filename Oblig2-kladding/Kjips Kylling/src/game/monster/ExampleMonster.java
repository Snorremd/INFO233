package game.monster;

import game.entity.types.Level;
import game.gfx.SpriteLoader;
import game.util.Direction;

import java.util.Random;

public class ExampleMonster extends AbstractMonster {
	/**
	 * 
	 * @param level brettet monsteret skal være på
	 * @param loader SpriteLoaderen som kan gi monsteret tilgang til spritesene sine.
	 * @param column Kolonnen (X-verdien) som monsteret skal begynne på.
	 * @param row Raden (Y-verdien) som monsteret skal begynne på.
	 */
	public ExampleMonster(Level level, SpriteLoader loader, int column, int row){
		/*
		 * Vi bruker altså den øverste raden i spritesheeten monstre
		 */
		super(column, row, level, loader, 0);
	}

	@Override
	public void tick() {
		/* Gå tilfeldig retning hver tick
		 * Dere bør kanskje gi deres monstre mer underholdende kommandoer. */
		Direction[] directions = Direction.values();
		Direction walkDir = directions[new Random().nextInt(directions.length)];
		System.out.printf("[INFO] ExampleMonster bestemte seg for å gå %s%n", walkDir);
		this.move(walkDir, level);
	}
}
