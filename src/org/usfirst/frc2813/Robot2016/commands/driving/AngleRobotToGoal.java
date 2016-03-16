package org.usfirst.frc2813.Robot2016.commands.driving;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.subsystems.TiltCameraDistanceCalculator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AngleRobotToGoal extends CommandGroup {

	double angleOffset;
	
    public AngleRobotToGoal() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angleOffset = TiltCameraDistanceCalculator.targetAngleOffset();
    	Robot.driveTrain.changeSetpoint(angleOffset);
    	System.out.println("Angle Offset: " + angleOffset);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.enablePID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.oi.getJoystick1().getRawButton(1)) return false;
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.disablePID();
    	Robot.driveTrain.setPointedAtGoal(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.disablePID();
    	Robot.driveTrain.setPointedAtGoal(false);
    }
}
