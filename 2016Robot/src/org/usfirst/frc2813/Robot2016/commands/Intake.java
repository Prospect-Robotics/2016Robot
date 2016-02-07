package org.usfirst.frc2813.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class Intake extends Command {

	public Intake() {
		requires(Robot.intake);
	}

	protected void initialize() {
		Robot.intake.move(1);
	}

	protected void execute() {

	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {
		Robot.intake.move(0);
	}
}