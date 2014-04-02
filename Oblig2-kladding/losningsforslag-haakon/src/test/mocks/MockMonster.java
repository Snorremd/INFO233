package test.mocks;

import java.awt.Graphics;

import game.entity.types.Level;
import game.entity.types.Monster;
import game.util.Direction;

public class MockMonster implements Monster {
	public byte priority;
	public MockMonster(byte priority){
		this.priority = priority;
	}
	@Override
	public void move(Direction dir, Level l) {
	}

	@Override
	public int getRow() {
		return 0;
	}

	@Override
	public int getColumn() {
		return 0;
	}

	@Override
	public void setPosition(int column, int row) {
	}

	@Override
	public void setDirection(Direction dir) {
	}

	@Override
	public void render(Graphics gfx) {
	}

	@Override
	public void tick() {
	}

	@Override
	public byte getPriority() {
		return (byte) priority;
	}

	@Override
	public String toString(){
		return String.format("MockMonster (Pri %d)", priority);
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
