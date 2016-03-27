package org.usfirst.frc2813.Robot2016.commands.driving;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.subsystems.TiltCameraDistanceCalculator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateRobotToGoal extends Command {

	double angleOffset;
	
    public RotateRobotToGoal() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	// Find out how much robot needs to turn, then tell driveTrain how much to turn
    	angleOffset = TiltCameraDistanceCalculator.targetAngleOffset();
    	Robot.driveTrain.modifySetpoint(angleOffset);
    	Robot.driveTrain.enablePID();
    	
    	System.out.println("Angle Offset: " + angleOffset);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.customPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false; // Keeps running until canceled
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.driveTrain.disablePID();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	Robot.driveTrain.disablePID();
    	
    }
}
