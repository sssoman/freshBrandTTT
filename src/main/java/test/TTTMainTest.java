package test;

import static org.junit.Assert.*;
import org.junit.Test;
import datamodel.TTT;

public class TTTMainTest {

	@Test
	public void testStart(){
		TTT ttt = new TTT(3, "test", "test");
		assertEquals("Board is not created correctly", 3, ttt.getBoard().length);
	}
	
	@Test
	public void testMove(){
		TTT ttt = new TTT(3, "test", "test");
		assertEquals("Board is created correctly", 3, ttt.getBoard().length);
		ttt.move(0, 0);
		assertEquals("Board is not populated correctly", 1, ttt.getBoard()[0][0]);
	}
	
	@Test
	public void testStatus(){
		TTT ttt = new TTT(3, "test", "test");
		assertEquals("Board is created correctly", 3, ttt.getBoard().length);
		ttt.move(0, 0);
		assertEquals("Board is not populated correctly", 1, ttt.getBoard()[0][0]);
		assertTrue("Board status not shown correctly", ttt.displayBoard().contains("X"));
	}
}
