package org.usfirst.frc2813.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class ShooterIntake extends Command {

	public ShooterIntake() {
		requires(Robot.intakeShooter);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.intakeShooter.spin(-1.0);

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {

	}

	protected void interrupted() {
		Robot.intakeShooter.spin(0);
	}
}