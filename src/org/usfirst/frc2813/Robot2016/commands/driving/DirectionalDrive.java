package org.usfirst.frc2813.Robot2016.commands.driving;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DirectionalDrive extends Command {

    public DirectionalDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double x = Robot.oi.getJoystick1().getX();
    	double y = -Robot.oi.getJoystick1().getY();
    	
    	Robot.driveTrain.directionalDrive(x, y);		
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
