package org.usfirst.frc2813.Robot2016.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class ElevatorDown extends Command {

	public ElevatorDown() {
		requires(Robot.elevator);
	}

	protected void initialize() {

	}

	protected void execute() {
		Robot.elevator.move(-0.6);
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {
		Robot.elevator.move(0);
	}
}