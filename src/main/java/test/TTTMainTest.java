package test;

import static org.junit.Assert.*;
import org.junit.Test;
import game.TTT;

public class TTTMainTest {

	@Test
	public void testStart(){
		TTT ttt = new TTT(3, "test", "test");
		startAsserts(ttt);
	}
	
	@Test
	public void testMove(){
		TTT ttt = new TTT(3, "test", "test");
		startAsserts(ttt);
		moveAndAssert(0, 0 , ttt);
	}
	
	@Test
	public void testStatus(){
		TTT ttt = new TTT(3, "test", "test");
		startAsserts(ttt);
		moveAndAssert(0, 0 , ttt);
		statusAssert(ttt, "X");
	}
	
	private void startAsserts(TTT ttt){
		assertEquals("Board is not created correctly", 3, ttt.getBoard().length);
	}
	
	private void moveAndAssert(int row, int col, TTT ttt){
		ttt.move(row, col);
		assertEquals("Board is not populated correctly", 1, ttt.getBoard()[row][col]);
	}
	
	private void statusAssert(TTT ttt, String symbol){
		assertTrue("Board status not shown correctly", ttt.displayBoard().contains(symbol));
	}
}
