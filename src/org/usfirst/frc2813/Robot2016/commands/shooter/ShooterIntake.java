package org.usfirst.frc2813.Robot2016.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class ShooterIntake extends Command {

	public ShooterIntake() {
		requires(Robot.shooterWheels);
	}

	protected void initialize() {
	}

	protected void execute() {
		
    	Robot.pneumatics.retractShooterPiston();
    	Robot.shooterWheels.setSetpoint(-24000);
    	
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}