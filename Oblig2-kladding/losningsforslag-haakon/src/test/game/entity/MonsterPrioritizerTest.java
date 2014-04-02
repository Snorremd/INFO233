package test.game.entity;

import static org.junit.Assert.assertTrue;
import game.entity.MonsterPrioritizer;
import game.entity.types.Monster;

import java.util.Comparator;

import org.junit.Test;

import test.mocks.MockMonster;

public class MonsterPrioritizerTest {
	Comparator<Monster> test = MonsterPrioritizer.INSTANCE; 
	
	@Test
	public void testCompare() {
		MockMonster[] monsters = {
				new MockMonster((byte) 0),
				new MockMonster((byte) 4),
				new MockMonster((byte) 4),
				new MockMonster((byte) -3),
				};
		assertTrue(test.compare(monsters[0], monsters[1]) > 0);
		assertTrue(test.compare(monsters[1], monsters[2]) == 0);
		assertTrue(test.compare(monsters[2], monsters[3]) < 0);
	}

}
