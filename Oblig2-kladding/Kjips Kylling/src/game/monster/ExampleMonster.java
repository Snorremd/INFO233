package game.monster;

import game.entity.types.Level;
import game.gfx.SpriteLoader;

public class ExampleMonster extends AbstractMonster {

	public ExampleMonster(Level level, SpriteLoader loader, int column, int row){
		super(column, row, level, loader, 0);
	}
	
	@Override
	public void tick() {
		/* Gå tilfeldig retning hver tick */
		
	}

}
