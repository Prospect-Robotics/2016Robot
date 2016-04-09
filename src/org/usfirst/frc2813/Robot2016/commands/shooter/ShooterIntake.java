package org.usfirst.frc2813.Robot2016.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class ShooterIntake extends Command {

	public ShooterIntake() {
		requires(Robot.shooterAim);
		requires(Robot.shooterWheels);
	}

	protected void initialize() {
		
    	Robot.pneumatics.retractShooterPiston();
    	Robot.shooterWheels.enableController();
    	Robot.shooterWheels.setSetpoint(-18000);
    	
	}

	protected void execute() {
		Robot.shooterAim.manualAim(-0.2);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.shooterAim.manualAim(0);
    	Robot.shooterWheels.setSetpoint(0);
	}

	protected void interrupted() {
		Robot.shooterAim.manualAim(0);
    	Robot.shooterWheels.setSetpoint(0);
	}
}