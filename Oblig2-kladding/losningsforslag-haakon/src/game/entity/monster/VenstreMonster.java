package game.entity.monster;

import game.entity.types.Level;
import game.util.Direction;
import game.util.Mover;
import game.view.gfx.SpriteLoader;

public class VenstreMonster extends AbstractMonster {
	private int tickCounter = 0;
	protected byte priority = 0;
	public VenstreMonster(Level level, SpriteLoader loader, int column, int row, byte priority){
		super(column, row, level, loader, 0);
		this.priority = priority;
		this.setDirection(Direction.SOUTH);
	}

	@Override
	public void tick() {
		
		/* Bare gjør noe hver nte tick. */
		int nteTick = 24;
		if((tickCounter++ % nteTick) != 0){
			return;
		}
		
		int[] pos = Mover.position(facingDirection, getColumn(), getRow());
		int col = pos[Mover.COLUMN];
		int row = pos[Mover.ROW]; 
		if((!level.walkable(col, row)) || level.isPlaceDeadly(col, row)){
			this.setDirection(Mover.turnLeft(facingDirection));		
		}
		pos = Mover.position(facingDirection, getColumn(), getRow());
		col = pos[Mover.COLUMN];
		row = pos[Mover.ROW]; 
		if(!level.isPlaceDeadly(col, row)){
			this.move(facingDirection, level);
		}
	}

	@Override
	public byte getPriority() {
		return priority;
	}

}
