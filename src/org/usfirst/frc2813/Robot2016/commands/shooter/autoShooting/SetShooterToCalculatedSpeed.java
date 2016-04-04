package org.usfirst.frc2813.Robot2016.commands.shooter.autoShooting;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetShooterToCalculatedSpeed extends Command {

    public SetShooterToCalculatedSpeed() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterWheels);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	// Calculates then sets optimal goal shooting speed
    	Robot.updateShootingValues();
    	Robot.shooterWheels.setSetpoint(Robot.shootingValues[1]);
    	Robot.shooterWheels.enableController();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.shooterWheels.getShooterSpeedSet();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
