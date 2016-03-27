package org.usfirst.frc2813.Robot2016.commands.shooter;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class SetShooterAngleToGoal extends Command {

    public SetShooterAngleToGoal() {
    	
    	requires(Robot.shooterAim);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	Robot.shooterAim.enablePID();
    	Robot.updateShootingValues();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double angle = Robot.shootingValues[0];
    	Robot.shooterAim.setSetpoint(angle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false; // Keep running until canceled
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
}
