package org.usfirst.frc2813.Robot2016.commands.obstacles;

import org.usfirst.frc2813.Robot2016.Robot;

public class ObstacleHelper {
	
	public static boolean isOnRamp() {
		
		double meanPitch = Robot.normalPitch[0];
		double stdDevPitch = Robot.normalPitch[1];
		double marginOfError = 3;
		
		if (meanPitch > -marginOfError && meanPitch < marginOfError
			&& stdDevPitch > -marginOfError && stdDevPitch < marginOfError)
			return false;
		return true;
		
	}
	
}
