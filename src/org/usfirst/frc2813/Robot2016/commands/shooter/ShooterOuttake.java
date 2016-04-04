package org.usfirst.frc2813.Robot2016.commands.shooter;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterOuttake extends Command {

    public ShooterOuttake() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.shooterWheels);
		
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.shooterWheels.enableController();
    	Robot.shooterWheels.setSetpoint(54000);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterWheels.setSetpoint(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooterWheels.setSetpoint(0);
    }
}
