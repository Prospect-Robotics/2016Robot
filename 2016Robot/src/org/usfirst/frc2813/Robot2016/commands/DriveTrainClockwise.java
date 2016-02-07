package org.usfirst.frc2813.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class DriveTrainClockwise extends Command {

	public DriveTrainClockwise() {
		requires(Robot.arms);
	}

	protected void initialize() {

	}

	protected void execute() {
		RotateWithPID.changeSetpoint(30);
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {
		
	}
}