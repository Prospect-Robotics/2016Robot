package org.usfirst.frc2813.Robot2016.commands;

import org.usfirst.frc2813.Robot2016.Robot;


public class RotateWithPID {
	
	public static void changeSetpoint(double setpoint) {
		double desiredValue = Robot.driveTrain.getOldSetpoint() + setpoint;
		Robot.driveTrain.setNewSetpoint(desiredValue);
	}
}
