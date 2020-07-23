/**
 * 
 */
package com.dineshvelhal.javaprojects.maths;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dinesh_Velhal
 * 
 * JUnit Test Cases - created for testing the Jenkins Unit Test step
 *
 */
public class MathTest {
	Math math;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("@Before Inside setUp");
		math = new Math();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("@After Inside tearDown\n*********************************");
		math = null;
	}

	@Test
	public void addTest() {
		System.out.println("@Test Inside addTest");
		
		assertEquals(5 + 0 + 0, math.add(2, 3));
	}
	
	@Test
	public void subtractTest() {
		System.out.println("@Test Inside subtractTest");
		
		assertEquals(5, math.subtract(7, 2));
	}
	
	@Test
	public void multiplyTest() {
		System.out.println("@Test Inside multiplyTest");
		
		assertEquals(15, math.multiply(5, 3));
	}
	
	@Test
	public void divideTest() {
		System.out.println("@Test Inside divideTest");
		
		assertEquals(5, math.divide(20, 4));
		assertNotEquals(5.5, math.divide(11, 2)); // to check it's integer division
	}

}
