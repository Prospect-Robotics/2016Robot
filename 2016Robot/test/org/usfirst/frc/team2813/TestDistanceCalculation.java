package org.usfirst.frc.team2813;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2813.vision.TiltCameraDistanceCalculator;

public class TestDistanceCalculation {

	TiltCameraDistanceCalculator calc;
	double epsilon = 0.1;
	
	@Before
	public void setUp() throws Exception {
		this.calc = new TiltCameraDistanceCalculator(600, 25.5, 1.0, 85/12.0, 9.7);
	}

	/*
	 * Not a great test. We are just checking to see if the results of the Java
	 * program are more or less the same as the Python version. Real-world tests
	 * are needed to validate the math
	 */
	@Test
	public void test() {
		assertTrue(Math.abs(calc.targetDistance(10) - 32.5) < epsilon);
		assertTrue(Math.abs(calc.targetDistance(300) - 8.7) < epsilon);
		assertTrue(Math.abs(calc.targetDistance(490) - 5.6) < epsilon);
	}

}
