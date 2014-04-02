package game.entity;

import game.entity.types.Monster;

import java.util.Comparator;

public enum MonsterPrioritizer implements Comparator<Monster> {
	INSTANCE;

	@Override
	public int compare(Monster o1, Monster o2) {
		return o2.getPriority() - o1.getPriority();
	}
}
