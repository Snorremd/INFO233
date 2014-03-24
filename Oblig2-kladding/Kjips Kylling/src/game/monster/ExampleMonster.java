package game.monster;

import game.entity.types.Level;
import game.gfx.SpriteLoader;
import game.util.Direction;
import game.util.Mover;

import java.util.ArrayList;
import java.util.Random;

/**
 * Et eksempel på et enkelt monster.
 * Går en tilfeldig rute per tick.
 * 
 * @author Haakon Løtveit (haakon.lotveit@student.uib.no)
 *
 */
public class ExampleMonster extends AbstractMonster {
	private int tickCounter = 0;
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
		/* Gå tilfeldig retning hver tick 250nde tick
		 * Dere bør gi deres monstre mer underholdende kommandoer. */

		
		/* Bare gjør noe hver nte tick. */
		int nteTick = 250;
		if((tickCounter++ % nteTick) != 0){
			return;
		}
		
		/* Gå i en tilfeldig retning */
		Direction[] directions = Direction.values();
		ArrayList<Direction> walkables = new ArrayList<>(4);
		for(Direction d : directions){
			int[] pos = Mover.position(d, getColumn(), getRow());
			if(level.walkable(pos[Mover.COLUMN], pos[Mover.ROW])){
				walkables.add(d);
			}
		}
		Direction walkDir = walkables.get(new Random().nextInt(walkables.size()));
		this.move(walkDir, level);
	}
}
