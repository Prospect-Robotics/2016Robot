package org.usfirst.frc2813.Robot2016.commands.obstacles;

import org.usfirst.frc2813.Robot2016.Robot;

public class ObstacleHelper {
	
	public static boolean isOnRamp() {

		Robot.updateSensorValues();
		
		double meanPitch = Robot.normalPitch[0];
		double stdDevPitch = Robot.normalPitch[1];
		double meanMarginOfError = 3;
		double stdDevMarginOfError = 0.8;
		
		if (meanPitch > -meanMarginOfError && meanPitch < meanMarginOfError
			&& stdDevPitch > -stdDevMarginOfError && stdDevPitch < stdDevMarginOfError)
			return false;
		return true;
		
	}
	
}
