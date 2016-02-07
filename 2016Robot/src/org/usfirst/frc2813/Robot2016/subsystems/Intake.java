package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private final SpeedController speedControllerLeft = RobotMap.intakeSpeedControllerLeft;
	private final SpeedController speedControllerRight = RobotMap.intakeSpeedControllerRight;

	public void initDefaultCommand() {

	}

	public void move(int speed) {
		speedControllerLeft.set(-speed);
		speedControllerRight.set(speed);
	}
}