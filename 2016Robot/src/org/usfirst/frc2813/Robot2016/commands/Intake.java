package org.usfirst.frc2813.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class Intake extends Command {

	public Intake() {
		requires(Robot.intakeShooter);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.intakeShooter.spin(-0.3);

	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {
		Robot.intakeShooter.spin(0);
	}
}