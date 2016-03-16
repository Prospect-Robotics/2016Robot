package org.usfirst.frc2813.Robot2016.commands.driving;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HaloDrive extends Command {

    public HaloDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double curveConstant = 0.5;
    	double joystickRotate = Robot.oi.getJoystick2().getZ(); 
    	double rotate = curveConstant * Math.pow(joystickRotate, 3) + (1 - curveConstant) * joystickRotate;
    	if (!Robot.driveTrain.getPIDStatus()) Robot.driveTrain.arcadeDrive(Robot.oi.getJoystick1().getY(), rotate);
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
