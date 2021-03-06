package org.usfirst.frc2813.Robot2016.commands.shooter.jogging;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterSensorJogUp extends Command {

    public ShooterSensorJogUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterAim);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooterAim.enablePID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterAim.modifySetpoint(0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
