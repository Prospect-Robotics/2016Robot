package org.usfirst.frc2813.Robot2016.subsystems;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.Ellipse2D;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;

import org.usfirst.frc2813.Robot2016.Robot;

//import javax.swing.JComponent;
//import javax.swing.JFrame;

public class TrajectorySimulator  {
	
	// Trajectory Algorithm (in cm) ***VALUES ARE NOT CURRENTLY ACCURATE***
	private static double changeInTime; /* The time (in seconds) added to the time at every
								  		 * iteration, can also be considered the
								  		 * resolution of the algorithm where
								  		 * resolution is indirectly proportional
								  		 * to this number
								  		 */
	
	// The distance between the camera and the goal (forward and backward axis)
	private static double distGoalX;

	// The distance between the camera and the goal (up and down axis)
	private static double distGoalY;

	private static double angleOfShooter; // In degrees
	private static double velocity; // the velocity of the ball (in cm/s)
	private static double velocityX;
	private static double velocityY;
	private static double x; // the position of the ball relative to its start (x is forward and backward)
	private static double y; // the position of the ball relative to its start (y is up and down)
	private static double accelerationX; // the acceleration of the ball where forward is positive (in cm/s)
	private static double accelerationY; // the acceleration of the ball where upward is positive (in cm/s)
	private static double ballWeight; // Ball weight (in g)
	private static double ballArea; // Area of ball's silhouette (in square centimeters)
	private static double airDensity; // Density of air (in g/[cube centimeters])
	private static double dragCoefficient; // Drag coefficient of a sphere (no unit)
	private static double dragConstant; // D, drag constant
	
	// This is for resetting velocity when new angle is selected
	private static double initialVelocity;
	
	// These are used for analyzing the values
	private static double[] bestAttemptValues;
	private static double minDistance;
	private static double minSpeed;
	private static double maxSpeed;
	private static double minAngle;
	private static double maxAngle;
	
	private static double actualDistGoalX;

	private static double actualDistGoalY;
	
	private static double timer;
	
	
	
	public static double[] findTrajectory() {
		
		changeInTime = 0.007;
		distGoalX = TiltCameraDistanceCalculator.targetDistance() + 43.18;
		distGoalY = 216.17;
		angleOfShooter = 10;
		velocity = 1850;
		velocityX = velocity * Math.cos(Math.toRadians(angleOfShooter));
		velocityY = velocity * Math.sin(Math.toRadians(angleOfShooter));
		x = 0;
		y = 0;
		ballWeight = 295;
		ballArea = 506.71;
		airDensity = 0.001225;
		dragCoefficient = 0.47;
		dragConstant = (airDensity * ballArea * dragCoefficient) / 2;
		initialVelocity = velocity;
		minDistance = -1;
		timer = System.currentTimeMillis();
		bestAttemptValues = new double[2];
		minAngle = 10;
		maxAngle = 58;
		
		System.out.println("Distance from goal: " + distGoalX);
		
//		// Close to mid range
//		if (Robot.varyShooterSpeeds) {
//			for (int i = 0; i <= 10; i++) {
//				
//				minSpeed = 50;
//				maxSpeed = 1295.4;
//				angleOfShooter = (maxAngle + minAngle) / 2;
//				boolean velocityTuned = false;
//				boolean foundValue = true;
//				
//				// Calculate actual starting position of ball (changes with angle of shooter)
//				actualDistGoalX = distGoalX - (35.56 * Math.cos(angleOfShooter));
//				actualDistGoalY = distGoalY - (35.56 * Math.sin(angleOfShooter));
//				
//				for (int j = 0; !velocityTuned && foundValue; j++) {
//					
//					// Calculate trajectory of ball until y-velocity is 0 or less
//					initialVelocity = (maxSpeed + minSpeed) / 2;
//					calculateTajectory(false);
//					
//					// Change initial velocity based on guessed error
//					if (x < actualDistGoalX - 1) {
//						minSpeed = initialVelocity;
//					} else if (x > actualDistGoalX + 3) {
//						maxSpeed = initialVelocity;
//					} else if (initialVelocity <= maxSpeed) velocityTuned = true;
//					if (j > 100 || (j > 10 && initialVelocity > maxSpeed)) foundValue = false;
//				}
//				
//				if (foundValue) {
//					double guessDistance = Math.sqrt(Math.pow(actualDistGoalX - x, 2) + Math.pow(actualDistGoalY - y, 2));
//					if (guessDistance < minDistance || minDistance == -1) {
//						minDistance = guessDistance;
//						bestAttemptValues[0] = angleOfShooter;
//						bestAttemptValues[1] = initialVelocity;
//					}
//
//					if (minDistance > 5) {
//						
//						if (y < actualDistGoalY) {
//							minAngle = angleOfShooter;
//						} else {
//							maxAngle = angleOfShooter;
//						}
//						
//					} else break;
//				} else angleOfShooter += 0.1;
//				
//			}
//		}
		
		// Long range
		if (minDistance == -1 || minDistance > 50) {
			
			initialVelocity = velocity;
			minAngle = 10;
			maxAngle = 58;
			
			// Calculate actual starting position of ball (changes with angle of shooter)
			actualDistGoalX = distGoalX - (35.56 * Math.cos(angleOfShooter));
			actualDistGoalY = distGoalY - (35.56 * Math.sin(angleOfShooter));
			
			for (int i = 0; i <= 10; i++) {

				angleOfShooter = (maxAngle + minAngle) / 2;
				
				calculateTajectory(true);
				
				double guessDistance = Math.abs(actualDistGoalY - y);
				if (guessDistance < minDistance || minDistance == -1) {
					minDistance = guessDistance;
					bestAttemptValues[0] = angleOfShooter;
					bestAttemptValues[1] = initialVelocity;
				}
				if (minDistance > 5) {
					if (y < actualDistGoalY) {
						minAngle = angleOfShooter;
					} else {
						maxAngle = angleOfShooter;
					}
				}
				
			}
			
		}
		
		if (minDistance == -1 || minDistance > 50) {
			System.out.println("The velocity of the shooter wheels would need to be higher than shooter can handle.");
			return new double[0];
		}
		else {
			
			System.out.println("Time (in milliseconds) for trajectory calculation: " + (System.currentTimeMillis() - timer));
			
			System.out.println("The shot was calculated to land " + minDistance + "cm from the goal.");
			
			angleOfShooter = bestAttemptValues[0];
			initialVelocity = bestAttemptValues[1];
			System.out.println("Angle: " + bestAttemptValues[0]);
			System.out.println("Velocity: " + bestAttemptValues[1]);
			return bestAttemptValues;
			
		}
	}
	
	private static void calculateTajectory(boolean longRange) {

		x = 0;
		y = 0;
		velocity = initialVelocity;
		velocityX = velocity * Math.cos(Math.toRadians(angleOfShooter));
		velocityY = velocity * Math.sin(Math.toRadians(angleOfShooter));
		
		for (double time = 0; !(y < 0 || (longRange && x > distGoalX) || (!longRange && velocityY <= 0)); time += changeInTime) { // This is the target condition
			
			accelerationX = -(dragConstant / ballWeight) * velocity * velocityX;
			accelerationY = -980 - ((dragConstant / ballWeight) * velocity * velocityY);
			velocityX += accelerationX * changeInTime;
			velocityY += accelerationY * changeInTime;
			velocity = Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
			x += (velocityX * changeInTime) + (0.5 * accelerationX * Math.pow(changeInTime, 2));
			y += (velocityY * changeInTime) + (0.5 * accelerationY * Math.pow(changeInTime, 2));
			
		}
		
	}
	
}
