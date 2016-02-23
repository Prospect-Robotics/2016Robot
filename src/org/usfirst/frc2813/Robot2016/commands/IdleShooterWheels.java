package org.usfirst.frc2813.Robot2016.commands;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IdleShooterWheels extends Command {

    public IdleShooterWheels() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot.shooterAim);
    	requires(Robot.shooterWheels);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	SmartDashboard.putNumber("IntakeShooterValue", -Robot.oi.getJoystick1().getZ());
//    	Robot.shooterAim.spin(-Robot.oi.getJoystick1().getY()); // Shooter spin
    	Robot.shooterWheels.spin(0); // Shooter spin
//    	
//    	Robot.shooterAim.angle(0.75 * Math.sqrt((-Robot.oi.getJoystick1().getZ() + 1.1)) - 1); // Shooter angle
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
