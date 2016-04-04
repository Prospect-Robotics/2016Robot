package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;

public class NetworkTables {
		
	private static int maxWidthIndex;
	
	private static double[] goalWidths;
	private static double[] goalXs;
	private static double[] goalYs;

	public static double[] findGoal() {

		try {
			
			// Get contour report
			goalWidths = Robot.table.getSubTable("myContoursReport").getNumberArray("width", new double[0]);
			goalXs = Robot.table.getSubTable("myContoursReport").getNumberArray("centerX", new double[0]);
			goalYs = Robot.table.getSubTable("myContoursReport").getNumberArray("centerY", new double[0]);
			
			// Picks the goal with the biggest width for optimal shooting space
			maxWidthIndex = 0;
			for (int i = 0; i < goalWidths.length; i++) {
				if ((double) goalWidths[i] > (double) goalWidths[maxWidthIndex]) maxWidthIndex = i;
			}
			
			double[] output = {goalXs[maxWidthIndex], goalYs[maxWidthIndex]};
			return output;
			
		} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
			
			return new double[0];
			
		} catch (Exception ex) {
			
			System.out.println("Image proccessing error: " + ex.getMessage());
			return new double[0];
			
		}
		
	}
	

}


