package org.usfirst.frc2813.Robot2016.commands.shooter;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IdleShooterWheels extends Command {

    public IdleShooterWheels() {
    	requires(Robot.shooterWheels);
    }
   
    protected void initialize() {
    }
    
    protected void execute() {
    	
    	Robot.shooterWheels.setSetpoint(0); // Shooter spin
    	
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    }
    
    protected void interrupted() {
    }
}
