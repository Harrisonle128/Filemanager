/**
 * @author harrisonle
 * Email: Hal117@ucsd.edu
 * 
 * This file represents j-unit tests for the Filesystem file.
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

public class FileSystemTest {
	
	@Test
	public void findFileByDateTest() {
		FileSystem file = new FileSystem();
		file.add("mySample.txt", "/root", "2021/02/01");
		file.add("Test.txt", "/home", "05/17/2023");
		file.add("File1.txt", "/root", "2021/02/01");
		file.add("File2.txt", "/home", "05/17/2023");
		
		ArrayList<String> expected = new ArrayList<>();
		expected.add("mySample.txt");
		expected.add("File1.txt");
		
		assertEquals(expected, file.findFileNamesByDate("2021/02/01"));
	}
	
	
	@Test
	public void outputDateTreeTest() {
		FileSystem file = new FileSystem();
		file.add("mySample.txt", "/home", "2021/02/01");
		file.add("mySample1.txt", "/root", "2021/02/01");
		file.add("mySample2.txt", "/user", "2021/02/06");
		
		List<String> expected = new ArrayList<>();
		expected.add("2021/02/06: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}");
		expected.add("2021/02/01: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}");
		expected.add("2021/02/01: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}");
		assertEquals(expected, file.outputDateTree());
	}
	
	@Test
	public void outputNameTreeTest() {
		FileSystem file = new FileSystem();
		file.add("mySample1.txt", "/root", "2021/02/01");
		
		List<String> expected = new ArrayList<>();
		expected.add("mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}");
		
		assertEquals(expected, file.outputNameTree());
	}
	
	
	
}