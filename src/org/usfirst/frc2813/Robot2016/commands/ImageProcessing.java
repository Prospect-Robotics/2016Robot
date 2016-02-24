package org.usfirst.frc2813.Robot2016.commands;
import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc2813.Robot2016.Robot;

public class ImageProcessing {
		
	private static int maxWidthIndex;
	private static double minX;
	private static double maxX;
	private static double minY;
	private static double maxY;
	
	private static double[] goalWidths;
	private static double[] goalHeights;
	private static double[] goalXs;
	private static double[] goalYs;
	private static double[] lineAngles;
	private static double[] lineLengths;
	private static double[] x1Coordinates;
	private static double[] y1Coordinates;
	private static double[] x2Coordinates;
	private static double[] y2Coordinates;
	
	private static double marginOfError = 8.5; // Difference between detected contour width and points of lines
	private static List<double[]> points = new ArrayList<double[]>();
	private static double[] point = new double[2];
	private static double[] bottomLeftPoint;
	private static double[] bottomRightPoint;
	private static double[] topLeftPoint;
	private static double[] topRightPoint;
	private static double[] targetPoint;
	private static double targetDistance;
	
	private static List<double[]> goalAngles = new ArrayList<double[]>();
	private static double goalAngle = -180;
	private static double angleError = 3;
	
	public static double[] findGoal() {

		try {


			// Populate angles list
			for (double angle = -45 + angleError/2; angle <= 45; angle += angleError) {
				double[] angleCount = {angle, 0};
				goalAngles.add(angleCount);
			}
			
			// Get contour report
			goalWidths = Robot.table.getSubTable("myContoursReport").getNumberArray("width");
			goalHeights = Robot.table.getSubTable("myContoursReport").getNumberArray("height");
			goalXs = Robot.table.getSubTable("myContoursReport").getNumberArray("centerX");
			goalYs = Robot.table.getSubTable("myContoursReport").getNumberArray("centerY");
			
			// Get line report
			lineAngles = Robot.table.getSubTable("myLinesReport").getNumberArray("angle");
			lineLengths = Robot.table.getSubTable("myLinesReport").getNumberArray("length");
			x1Coordinates = Robot.table.getSubTable("myLinesReport").getNumberArray("x1");
			y1Coordinates = Robot.table.getSubTable("myLinesReport").getNumberArray("y1");
			x2Coordinates = Robot.table.getSubTable("myLinesReport").getNumberArray("x2");
			y2Coordinates = Robot.table.getSubTable("myLinesReport").getNumberArray("y2");
			
			
			// Picks the goal with the biggest width for optimal shooting space
			maxWidthIndex = 0;
			for (int i = 0; i < goalWidths.length; i++) {
				if ((double) goalWidths[i] > (double) goalWidths[maxWidthIndex]) maxWidthIndex = i;
			}
			
//			// Sets the goal bounds based on contour report
//			minX = (double) goalXs[maxWidthIndex] - (double) goalWidths[maxWidthIndex]/2;
//			maxX = (double) goalXs[maxWidthIndex] + (double) goalWidths[maxWidthIndex]/2;
//			minY = (double) goalYs[maxWidthIndex] - (double) goalHeights[maxWidthIndex]/2;
//			maxY = (double) goalYs[maxWidthIndex] + (double) goalHeights[maxWidthIndex]/2;
//
//			// Get all the points form the line detection that are inside the contour bounds
//			for (int i = 0; i < x1Coordinates.length; i++) {
//				
//				double x1 = (double) x1Coordinates[i];
//				double y1 = (double) y1Coordinates[i];
//				double x2 = (double) x2Coordinates[i];
//				double y2 = (double) y2Coordinates[i];
//				double angle = (double) lineAngles[i];
//				double length = (double) lineLengths[i];
//				if (x1 >= minX - marginOfError && x1 <= maxX + marginOfError &&
//					x2 >= minX - marginOfError && x2 <= maxX + marginOfError &&
//					y1 >= minY - marginOfError && y1 <= maxY + marginOfError &&
//					y2 >= minY - marginOfError && y2 <= maxY + marginOfError) {
//					point[0] = x1;
//					point[1] = y1;
//					points.add(point);
//					point[0] = x2;
//					point[1] = y2;
//					points.add(point);
//					if (length > 20) {
//						angle = Math.abs(angle);
//						angle -= 90;
//						if (Math.abs(angle) > 45) {
//							if (angle > 45) {
//								angle -= 45;
//								angle = 45 - angle;
//							}
//							else {
//								angle += 45;
//								angle = -(45 + angle);
//							}
//							goalAngles.get((int) Math.round((angle + 43.5) / angleError))[1]++;
//						}
//					}
//				}
//				
//			}
//			
//			// Get angle of goal
//			int numberOfOccurances = 0;
//			for (int i = 0; i < goalAngles.size(); i++) {
//				if (goalAngles.get(i)[1] > numberOfOccurances) {
//					goalAngle = goalAngles.get(i)[0];
//					numberOfOccurances = (int) goalAngles.get(i)[1];
//				}
//			}
//			if (numberOfOccurances == 0) {
//				return new double[0];
//			}
//
//			// Save all of the corner points
//			double[] bottomMostPoint = points.get(0);
//			for (double[] point : points) {
//				if (point[1] > bottomMostPoint[1])
//					bottomMostPoint = point;
//			}
//			bottomLeftPoint = bottomMostPoint;
//			bottomRightPoint = bottomMostPoint;
//			topLeftPoint = bottomMostPoint;
//			topRightPoint = bottomMostPoint;
//			if ((double) bottomMostPoint[0] < (double) goalXs[maxWidthIndex]) {
//				for (double[] point : points) {
//					if (point[0] > bottomRightPoint[0] && point[1] > (double) goalYs[maxWidthIndex])
//						bottomRightPoint = point;
//					if (point[1] < topRightPoint[1] && point[0] > (double) goalXs[maxWidthIndex])
//						topRightPoint = point;
//				}
//				point[0] = topRightPoint[0] - (bottomRightPoint[0] - bottomLeftPoint[0]);
//				point[1] = topRightPoint[1] + (bottomLeftPoint[1] - bottomRightPoint[1]);
//				targetPoint = point;
//				targetDistance = 10000;
//				for (double[] point : points) {
//					double currentDistance = Math.sqrt(Math.pow(targetPoint[0] - point[0], 2) + Math.pow(targetPoint[1] - point[1], 2));
//					if (currentDistance < targetDistance) {
//						topLeftPoint = point;
//						targetDistance = currentDistance;
//					}
//				}
//			}
//			else {
//				for (double[] point : points) {
//					if (point[0] <= bottomLeftPoint[0] && point[1] > (double) goalYs[maxWidthIndex])
//						bottomLeftPoint = point;
//					if (point[1] < topLeftPoint[1] && point[0] < (double) goalXs[maxWidthIndex])
//						topLeftPoint = point;
//				}
//				point[0] = topLeftPoint[0] + (bottomRightPoint[0] - bottomLeftPoint[0]);
//				point[1] = topLeftPoint[1] + (bottomRightPoint[1] - bottomLeftPoint[1]);
//				targetPoint = point;
//				targetDistance = 10000;
//				for (double[] point : points) {
//					double currentDistance = Math.sqrt(Math.pow(targetPoint[0] - point[0], 2) + Math.pow(targetPoint[1] - point[1], 2));
//					if (currentDistance < targetDistance) {
//						topRightPoint = point;
//						targetDistance = currentDistance;
//					}
//				}
//			}
//			// Clear lists for next iteration
//			points.clear();
//			goalAngles.clear();
//			
//			double[] output = {topLeftPoint[0], topLeftPoint[1], topRightPoint[0], topRightPoint[1], bottomLeftPoint[0], bottomLeftPoint[1], bottomRightPoint[0], bottomRightPoint[1], goalXs[maxWidthIndex], goalYs[maxWidthIndex]};
			double[] output = {0, 1, 0, 1, 0, 1, 0, 1, goalXs[maxWidthIndex], goalYs[maxWidthIndex]};
			return output;
			
		} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
			
			// Clear lists for next iteration
			points.clear();
			goalAngles.clear();
			return new double[0];
		} catch (Exception ex) {
			// Clear lists for next iteration
			points.clear();
			goalAngles.clear();
			System.out.println("Image proccessing error: " + ex.getMessage());
			return new double[0];
		}
		
	}
	

}


