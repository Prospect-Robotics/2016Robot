package org.usfirst.frc2813.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class Push extends Command {

	public Push() {
		requires(Robot.arms);
	}

	protected void initialize() {

	}

	protected void execute() {
		Robot.arms.pull(1);
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {
		Robot.arms.pull(0);
	}
}