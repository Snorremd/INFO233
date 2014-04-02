package game.entity.monster;

import game.entity.types.Level;
import game.util.Direction;
import game.view.gfx.SpriteLoader;

public class PatruljeMonster extends AbstractMonster {
	protected byte priority;
	protected String rute;
	protected int currentStep = 0;
	private int tickCounter = 0;
	
	public PatruljeMonster(Level level, SpriteLoader loader, int column, int row, byte priority, String rute){
		super(column, row, level, loader, 5);
		this.priority = priority;		
		this.rute = rute;
		this.setDirection(Direction.SOUTH);
	}

	@Override
	public void tick() {
		/* Bare gjør noe hver nte tick. */
		int nteTick = 40;
		if((tickCounter++ % nteTick) != 0){
			return;
		}
		
		switch(rute.charAt(currentStep)){
		case 'N':
			setDirection(Direction.NORTH);
			break;
		case 'W':
			setDirection(Direction.WEST);
			break;
		case 'E':
			setDirection(Direction.EAST);
			break;
		case 'S':
			setDirection(Direction.SOUTH);
			break;
		default:
			System.err.printf("Ukjent retning %c%n", rute.charAt(currentStep));
		}
		this.move(facingDirection, level);
		
		currentStep = (currentStep + 1) % rute.length(); /* den klassiske wrap-around hacken */
	}

	@Override
	public byte getPriority() {
		return priority;
	}

}
