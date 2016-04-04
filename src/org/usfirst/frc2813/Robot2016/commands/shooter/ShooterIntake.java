package org.usfirst.frc2813.Robot2016.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class ShooterIntake extends Command {

	public ShooterIntake() {
		requires(Robot.shooterWheels);
	}

	protected void initialize() {
		
    	Robot.pneumatics.retractShooterPiston();
    	Robot.shooterWheels.enableController();
    	Robot.shooterWheels.setSetpoint(-24000);
    	
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
    	Robot.shooterWheels.setSetpoint(0);
	}

	protected void interrupted() {
    	Robot.shooterWheels.setSetpoint(0);
	}
}