package game.entity.monster;

import java.util.ArrayList;

import game.entity.SimplePlayer;
import game.entity.types.Level;
import game.util.Direction;
import game.util.Mover;
import game.view.gfx.SpriteLoader;

public class MålsøkendeMonster extends AbstractMonster {
	protected byte priority;
	private int tickCounter = 0;
	
	public MålsøkendeMonster(Level level, SpriteLoader loader, int column, int row, byte priority){
		super(column, row, level, loader, 4);
		this.priority = priority;		
		this.setDirection(Direction.SOUTH);
	}

	@Override
	public void tick() {
		/* Bare gjør noe hver nte tick. */
		int nteTick = 20;
		if((tickCounter++ % nteTick) != 0){
			return;
		}
		
		/* Finn ut hvilken retning som peker mest mot spilleren */		
		int deltaCol = SimplePlayer.getInstance().getColumn() - this.getColumn();
		int deltaRow = SimplePlayer.getInstance().getRow() - this.getRow();
		if(deltaCol == 0 && deltaRow == 0){
			return; /* Vi er oppå spilleren, så vi gjør ingenting. OMNOMNOM! */
		}

		/* Finn ut hvilke retninger vi kan gå i */
		ArrayList<Direction> directions = new ArrayList<>(4);
		for(Direction d : Direction.values()){
			int[] pos = Mover.position(d, getColumn(), getRow());
			int col = pos[Mover.COLUMN];
			int row = pos[Mover.ROW];
			if(level.walkable(col, row) && !level.isPlaceDeadly(col, row)){
				directions.add(d);
			}
		}
		if(directions.isEmpty()){
			return; /* We can't go anywere. So we return */
		}
		/* Sammenlign alle retningene. Den som det er best for oss å gå, går vi til. */
		Direction closerDirection = Direction.SOUTH;
		int distance = Integer.MAX_VALUE;
		for(Direction d : directions){
			int newDistance = manhattanDistanceToPlayer(Mover.position(d, getColumn(), getRow()));
			if(newDistance < distance){
				distance = newDistance;
				closerDirection = d;
			}
		}
		
		/* Prøv å gå dit */
		int[] pos = Mover.position(closerDirection, getColumn(), getRow());
		int col = pos[Mover.COLUMN];
		int row = pos[Mover.ROW];
		if(level.walkable(col, row) && !level.isPlaceDeadly(col, row)){
			this.move(closerDirection, level);
		}
	}

	private int manhattanDistanceToPlayer(int[] pos){
		int mCol = pos[Mover.COLUMN];
		int mRow = pos[Mover.ROW];
		int pCol = SimplePlayer.getInstance().getColumn();
		int pRow = SimplePlayer.getInstance().getRow();
		
		return Math.abs(mCol - pCol) + Math.abs(mRow - pRow);
	}
	
	@Override
	public byte getPriority() {
		return priority;
	}

}
