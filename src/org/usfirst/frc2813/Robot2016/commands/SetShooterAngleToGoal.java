package org.usfirst.frc2813.Robot2016.commands;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetShooterAngleToGoal extends Command {

    public SetShooterAngleToGoal() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterAim);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angle = Robot.autoShooterValues[0];
    	Robot.shooterAim.setAngle(angle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.shooterAim.getPointedAtGoal()) return true;
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterAim.disablePID();
    	Robot.shooterAim.setPointedAtGoal(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooterAim.disablePID();
    	Robot.shooterAim.setPointedAtGoal(false);
    }
    
}
