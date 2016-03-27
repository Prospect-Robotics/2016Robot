package org.usfirst.frc2813.Robot2016.commands.obstacles;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveUntilOffRamp extends Command {

    public DriveUntilOffRamp() {
    	requires(Robot.driveTrain);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.driveTrain.arcadeDrive(-0.82, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !ObstacleHelper.isOnRamp(); // Drives until off ramp
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.arcadeDrive(0, 0); // Stop moving after off ramp
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.arcadeDrive(0, 0); // Stop moving after interrupted
    }
}
