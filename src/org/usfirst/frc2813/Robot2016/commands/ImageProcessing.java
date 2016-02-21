package org.usfirst.frc2813.Robot2016.commands;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private static List<Point2D.Double> points = new ArrayList<Point2D.Double>();
	private static Point2D.Double bottomLeftPoint;
	private static Point2D.Double bottomRightPoint;
	private static Point2D.Double topLeftPoint;
	private static Point2D.Double topRightPoint;
	private static Point2D.Double targetPoint;
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
			
			// Sets the goal bounds based on contour report
			minX = (double) goalXs[maxWidthIndex] - (double) goalWidths[maxWidthIndex]/2;
			maxX = (double) goalXs[maxWidthIndex] + (double) goalWidths[maxWidthIndex]/2;
			minY = (double) goalYs[maxWidthIndex] - (double) goalHeights[maxWidthIndex]/2;
			maxY = (double) goalYs[maxWidthIndex] + (double) goalHeights[maxWidthIndex]/2;

			// Get all the points form the line detection that are inside the contour bounds
			for (int i = 0; i < x1Coordinates.length; i++) {
				
				double x1 = (double) x1Coordinates[i];
				double y1 = (double) y1Coordinates[i];
				double x2 = (double) x2Coordinates[i];
				double y2 = (double) y2Coordinates[i];
				double angle = (double) lineAngles[i];
				double length = (double) lineLengths[i];
				if (x1 >= minX - marginOfError && x1 <= maxX + marginOfError &&
					x2 >= minX - marginOfError && x2 <= maxX + marginOfError &&
					y1 >= minY - marginOfError && y1 <= maxY + marginOfError &&
					y2 >= minY - marginOfError && y2 <= maxY + marginOfError) {

					points.add(new Point2D.Double(x1, y1));
					points.add(new Point2D.Double(x2, y2));
					if (length > 20) {
						angle = Math.abs(angle);
						angle -= 90;
						if (Math.abs(angle) > 45) {
							if (angle > 45) {
								angle -= 45;
								angle = 45 - angle;
							}
							else {
								angle += 45;
								angle = -(45 + angle);
							}
							goalAngles.get((int) Math.round((angle + 43.5) / angleError))[1]++;
						}
					}
				}
				
			}
			
			// Get angle of goal
			int numberOfOccurances = 0;
			for (int i = 0; i < goalAngles.size(); i++) {
				if (goalAngles.get(i)[1] > numberOfOccurances) {
					goalAngle = goalAngles.get(i)[0];
					numberOfOccurances = (int) goalAngles.get(i)[1];
				}
			}
			if (numberOfOccurances == 0) {
				double[] output = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				return output;
			}

			// Save all of the corner points
			Point2D.Double bottomMostPoint = points.get(0);
			for (Point2D.Double point : points) {
				if (point.getY() > bottomMostPoint.getY())
					bottomMostPoint = point;
			}
			bottomLeftPoint = bottomMostPoint;
			bottomRightPoint = bottomMostPoint;
			topLeftPoint = bottomMostPoint;
			topRightPoint = bottomMostPoint;
			if ((double) bottomMostPoint.getX() < (double) goalXs[maxWidthIndex]) {
				for (Point2D.Double point : points) {
					if (point.getX() > bottomRightPoint.getX() && point.getY() > (double) goalYs[maxWidthIndex])
						bottomRightPoint = point;
					if (point.getY() < topRightPoint.getY() && point.getX() > (double) goalXs[maxWidthIndex])
						topRightPoint = point;
				}

				targetPoint = new Point2D.Double(topRightPoint.getX() - (bottomRightPoint.getX() - bottomLeftPoint.getX()), topRightPoint.getY() + (bottomLeftPoint.getY() - bottomRightPoint.getY()));
				targetDistance = 10000;
				for (Point2D.Double point : points) {
					double currentDistance = Math.sqrt(Math.pow(targetPoint.getX() - point.getX(), 2) + Math.pow(targetPoint.getY() - point.getY(), 2));
					if (currentDistance < targetDistance) {
						topLeftPoint = point;
						targetDistance = currentDistance;
					}
				}
			}
			else {
				for (Point2D.Double point : points) {
					if (point.getX() <= bottomLeftPoint.getX() && point.getY() > (double) goalYs[maxWidthIndex])
						bottomLeftPoint = point;
					if (point.getY() < topLeftPoint.getY() && point.getX() < (double) goalXs[maxWidthIndex])
						topLeftPoint = point;
				}
				
				targetPoint = new Point2D.Double(topLeftPoint.getX() + (bottomRightPoint.getX() - bottomLeftPoint.getX()), topLeftPoint.getY() + (bottomRightPoint.getY() - bottomLeftPoint.getY()));
				targetDistance = 10000;
				for (Point2D.Double point : points) {
					double currentDistance = Math.sqrt(Math.pow(targetPoint.getX() - point.getX(), 2) + Math.pow(targetPoint.getY() - point.getY(), 2));
					if (currentDistance < targetDistance) {
						topRightPoint = point;
						targetDistance = currentDistance;
					}
				}
			}
			// Clear lists for next iteration
			points.clear();
			goalAngles.clear();

			// Print results for testing
			SmartDashboard.putString("GoalCenter", "(" + goalXs[maxWidthIndex] + ", " + goalYs[maxWidthIndex] + ")");
			SmartDashboard.putBoolean("Goal", true);
			
			double[] output = {topLeftPoint.getX(), topLeftPoint.getY(), topRightPoint.getX(), topRightPoint.getY(), bottomLeftPoint.getX(), bottomLeftPoint.getY(), bottomRightPoint.getX(), bottomRightPoint.getY(), goalXs[maxWidthIndex], goalYs[maxWidthIndex]};
			return output;
			
		} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
			
			// Clear lists for next iteration
			points.clear();
			goalAngles.clear();
			SmartDashboard.putBoolean("Goal", false);
			double[] output = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
			return output;
		} catch (Exception ex) {
			
			// Clear lists for next iteration
			points.clear();
			goalAngles.clear();
			SmartDashboard.putString("GoalError", ex.getMessage());
			double[] output = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
			return output;
		}

	}
	

}


