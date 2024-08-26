/**
 * @author harrisonle
 * Email: Hal117@ucsd,edu
 * 
 * This file represents j-unit tests for the BST file. 
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.*;

public class BSTTest {
	

	/* TODO: Add your own tests */
	private BST<Integer, String> bst;
	
	@Before
	public void setUp() {
		bst = new BST<>();
	}

	@Test
	public void putTest() {
		
		bst.put(1, "red");
		bst.put(2, "green");
		
		assertEquals(2, bst.size());
		assertTrue(bst.containsKey(1));
	}
	
	@Test
	public void setTest() {
		bst.set(1, "red");
		bst.set(2, "green");
		
		assertEquals("green", bst.get(2));
		assertEquals("red", bst.get(1));
	}
	
	@Test
	public void removeKeyNotExistsTest() {
	    bst.put(5, "Apple");
	    bst.put(3, "Banana");
	    bst.put(7, "Orange");

	    bst.remove(7);

	    assertEquals(2, bst.size());
	    assertTrue(bst.containsKey(5));
	    assertTrue(bst.containsKey(3));
	    assertFalse(bst.containsKey(7));
	}
}

