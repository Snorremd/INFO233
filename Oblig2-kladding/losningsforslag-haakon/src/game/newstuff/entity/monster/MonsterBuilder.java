package game.newstuff.entity.monster;

import game.entity.monster.HøyreMonster;
import game.entity.monster.MålsøkendeMonster;
import game.entity.monster.OppNedMonster;
import game.entity.monster.PatruljeMonster;
import game.entity.monster.VenstreHøyreMonster;
import game.entity.monster.VenstreMonster;
import game.entity.types.Level;
import game.entity.types.Monster;
import game.view.gfx.SpriteLoader;

/**
 * Half factory, half builder.
 * Useful thing anyhow.
 * And Emacs' code generation makes this stuff very easy.
 * @author Haakon Løtveit (haakon.lotveit@student.uib.no)
 *
 */
public class MonsterBuilder {
	Level level;
	SpriteLoader loader;
	int column, row;
	byte priority;
	String rute;
	String type;
	public MonsterBuilder(){

	}

	/* Alt dette er automnatisk laget av emacs. */
	public MonsterBuilder level(Level level){
		this.level = level;
		return this;
	}
	public MonsterBuilder loader(SpriteLoader loader){
		this.loader = loader;
		return this;
	}
	public MonsterBuilder column(int column){
		this.column = column;
		return this;
	}
	public MonsterBuilder row(int row){
		this.row = row;
		return this;
	}
	public MonsterBuilder priority(byte priority){
		this.priority = priority;
		return this;
	}
	public MonsterBuilder rute(String rute){
		this.rute = rute;
		return this;
	}

	public MonsterBuilder type(String type){
		this.type = type;
		return this;
	}

	public Monster create(){
		switch(type){
		case "HøyreMonster":
			return new HøyreMonster(level, loader, column, row, priority);
		case "MålsøkendeMonster":
			return new MålsøkendeMonster(level, loader, column, row, priority);
		case "OppNedMonster":
			return new OppNedMonster(level, loader, column, row, priority);
		case "PatruljeMonster":
			return new PatruljeMonster(level, loader, column, row, priority, rute);
		case "VenstreHøyreMonster":
			return new VenstreHøyreMonster(level, loader, column, row, priority);
		case "VenstreMonster":
			return new VenstreMonster(level, loader, column, row, priority);
		default:
			System.out.printf("[ERROR] Monster type %s does not exist %n", type);
			return null;
		}
	}
}
