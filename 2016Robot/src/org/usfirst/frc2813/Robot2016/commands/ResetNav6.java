package org.usfirst.frc2813.Robot2016.commands;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetNav6 extends Command {

	public ResetNav6() {
		requires(Robot.nav6);
	}

	protected void initialize() {

	}

	protected void execute() {
		Robot.nav6.resetNav6();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {

	}
}