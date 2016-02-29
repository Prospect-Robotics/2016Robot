package org.usfirst.frc2813.Robot2016.commands.shooter;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.Ellipse2D;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.subsystems.TiltCameraDistanceCalculator;

//import javax.swing.JComponent;
//import javax.swing.JFrame;

public class TrajectorySimulator  {


	
	private static double timer;
	
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
	private static double time; // time (in seconds) since the ball has been launched
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
	
//	private static Random rand;
//	
//	private static int width;
//	private static int height;
//	private static JFrame frame = new JFrame();
	
	
	
	public static double[] findTrajectory() {
//		width = 1600;
//		height = 500;
//		frame.setSize(width, height);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		changeInTime = 0.007;
		distGoalX = TiltCameraDistanceCalculator.targetDistance((int) Math.round(Robot.centerY)) + 43.18;
		distGoalY = 216.17;
		angleOfShooter = 10;
		velocity = 1295.4;
		velocityX = velocity * Math.cos(Math.toRadians(angleOfShooter));
		velocityY = velocity * Math.sin(Math.toRadians(angleOfShooter));
		time = 0;
		x = 0;
		y = 0;
		ballWeight = 295;
		ballArea = 506.71;
		airDensity = 0.001225;
		dragCoefficient = 0.47;
		dragConstant = (airDensity * ballArea * dragCoefficient) / 2;
		initialVelocity = velocity;
//		rand = new Random();
		minDistance = -1;
		timer = System.currentTimeMillis();
		bestAttemptValues = new double[2];
		minAngle = 10;
		maxAngle = 90;
		
		System.out.println("Distance from goal: " + distGoalX);
		
		// Close to mid range
//		for (int i = 0; true; i++) {
//			
//			minSpeed = 0;
//			maxSpeed = 1700;
//			angleOfShooter = (maxAngle + minAngle) / 2;
//			boolean velocityTuned = false;
//			boolean foundValue = true;
//			
//			// Calculate actual starting position of ball (changes with angle of shooter)
//			actualDistGoalX = distGoalX - (35.56 * Math.cos(angleOfShooter));
//			actualDistGoalY = distGoalY - (35.56 * Math.sin(angleOfShooter));
//			
//			for (int j = 0; !velocityTuned && foundValue; j++) {
//				
//				// Calculate trajectory of ball until y-velocity is 0 or less
//				initialVelocity = (maxSpeed + minSpeed) / 2;
//				calculateTajectory(false, false, false);
//				
//				// Change initial velocity based on guessed error
//				if (x < actualDistGoalX - 1) {
//					minSpeed = initialVelocity;
//				} else if (x > actualDistGoalX + 3) {
//					maxSpeed = initialVelocity;
//				} else {
//					if (initialVelocity <= 1700) velocityTuned = true;
//				}
//				if (j > 100 || (j > 10 && initialVelocity > 1700)) foundValue = false;
//			}
//			
//			if (foundValue) {
//				double guessDistance = Math.sqrt(Math.pow(actualDistGoalX - x, 2) + Math.pow(actualDistGoalY - y, 2));
//				if (guessDistance < minDistance || minDistance == -1) {
//					minDistance = guessDistance;
//					bestAttemptValues[0] = angleOfShooter;
//					bestAttemptValues[1] = initialVelocity;
////					System.out.println("[Angle, Velocity]: " + "[" + angleOfShooter + ", " + velocity + "]");
////					System.out.println("[x, y]: " + "[" + x + ", " + y + "]");
//				}
////				System.out.println("ANGLE, DISTANCE: " + angleOfShooter + ", " + guessDistance);
////				Thread.sleep(300);
////				point(angleOfShooter, guessDistance + 200, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
//				if (minDistance > 5) {
//					
//					if (y < actualDistGoalY) {
//						minAngle = angleOfShooter;
//					} else {
//						maxAngle = angleOfShooter;
//					}
//					
//				} else break;
//			} else angleOfShooter += 0.1;
//			
//			if (i > 10) break;
//			
//		}
		
		// Long range
		if (minDistance == -1 || minDistance > 50) {
			
			initialVelocity = 1295.4;
			minAngle = 10;
			maxAngle = 90;
			
			// Calculate actual starting position of ball (changes with angle of shooter)
			actualDistGoalX = distGoalX - (35.56 * Math.cos(angleOfShooter));
			actualDistGoalY = distGoalY - (35.56 * Math.sin(angleOfShooter));
			
			for (int i = 0; true; i++) {

				angleOfShooter = (maxAngle + minAngle) / 2;
				
				calculateTajectory(true, false, false);
				
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
				
				if (i > 10) break;
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
//			initialVelocity = 1700;
//			angleOfShooter = 26;
//			calculateTajectory(true, true, false);
			return bestAttemptValues;
		}
//		point(distGoalX, distGoalY - 30.48, Color.GREEN);
//		frame.add(new goalDrawing(distGoalX, distGoalY - 10, 20, 60.96));
//		frame.setVisible(true);
//		initialVelocity = 300;
//		angleOfShooter = 10;
//		while (true) {
//			if (initialVelocity < 1700) initialVelocity += 20;
//			else {
//				initialVelocity = 300;
//				angleOfShooter += 5;
//			}
//			calculateTajectory(true, true, false);
//		}
	}
	
	private static void calculateTajectory(boolean longRange, boolean plotPoints, boolean logValues) {

		x = 0;
		y = 0;
		velocity = initialVelocity;
		velocityX = velocity * Math.cos(Math.toRadians(angleOfShooter));
		velocityY = velocity * Math.sin(Math.toRadians(angleOfShooter));
//		Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
		
		for (time = 0; true; time += changeInTime) { // This is the target condition
			
			if (logValues) {
//				System.out.println("x: " + x);
//				System.out.println("y: " + y);
//				System.out.println("velocityX: " + velocityX);
//				System.out.println("velocityY: " + velocityY);
//				System.out.println("time: " + time);
//				System.out.println();
			}

			if (y < 0) break;
//			if (plotPoints) point(x, y, color);
			else if (longRange && x > distGoalX) break; 
			else if (!longRange && velocityY <= 0) break;
			
			accelerationX = -(dragConstant / ballWeight) * velocity * velocityX;
			accelerationY = -980 - ((dragConstant / ballWeight) * velocity * velocityY);
			velocityX += accelerationX * changeInTime;
			velocityY += accelerationY * changeInTime;
			velocity = Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
			x += (velocityX * changeInTime) + (0.5 * accelerationX * Math.pow(changeInTime, 2));
			y += (velocityY * changeInTime) + (0.5 * accelerationY * Math.pow(changeInTime, 2));
			
		}
	}
	
//	private static void point(double x, double y, Color color) {
//		frame.add(new mirrorPoint(x, y, color));
//		frame.setVisible(true);
//	}
}

//class goalDrawing extends JComponent {
//
//	private static final long serialVersionUID = -6725559481831399216L;
//	
//	private double x, y, width, height;
//	
//	public goalDrawing(double x, double y, double width, double height) {
//		this.x = x;
//		this.y = y;
//		this.width = width;
//		this.height = height;
//	}
//	
//	public void paintComponent(Graphics g) {
//		g.setColor(Color.BLUE);
//		g.fillRect((int) Math.round(x), (int) Math.round(-y + getHeight()), (int) Math.round(width), (int) Math.round(height));
//	}
//	
//}
//
//// Credit to Daniel Wart of Prospect Robotics 2813 for the code below
//class mirrorPoint extends JComponent{
//	
//	private static final long serialVersionUID = 3714765966168198206L;
//	
//	private double x;
//	private double y;
//	private Color color;
//	
//	public mirrorPoint(double x, double y, Color color){
//		this.x = x;
//		this.y = y;
//		this.color = color;
//	}
//	
//	public void paintComponent(Graphics g){
//
//		double[] ellipsePos = {this.x,this.y};
//		double[] ellipseSize = {10,10};
//		double[] flippedCoords = flipXY(ellipsePos[0] - ellipseSize[0]/2, ellipsePos[1] - ellipseSize[1]/2, getWidth() , getHeight());
//		g.setColor(this.color);
//		((Graphics2D) g).fill(new Ellipse2D.Double(flippedCoords[0], flippedCoords[1], ellipseSize[0], ellipseSize[1]));
//
//	}
//	
//	private double[] flipXY(double x, double y,int width,int height){
//		
//		return new double[] {x,-y+height};
//		
//	}
//	
//}

