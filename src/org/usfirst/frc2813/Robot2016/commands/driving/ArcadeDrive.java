package org.usfirst.frc2813.Robot2016.commands.driving;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

	public ArcadeDrive() {
		requires(Robot.driveTrain);
	}

	protected void initialize() {

	}

	protected void execute() {
		if (!Robot.driveTrain.getPIDStatus()) {
			Robot.driveTrain.arcadeDrive(Robot.oi.getJoystick1().getY(), Robot.oi
					.getJoystick1().getX());
		}
		
		// SmartDashboard.putNumber("JoysitckX",
		// Robot.oi.getJoystick1().getX());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {

	}

	protected void interrupted() {

	}
}