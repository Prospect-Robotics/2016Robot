package org.usfirst.frc2813.Robot2016.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc2813.Robot2016.Robot;

public class Shoot extends CommandGroup {

    public Shoot() {
    	requires(Robot.shooterWheels);
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	Robot.shooterWheels.spin(1);
    	Timer.delay(2);
    	Robot.pneumatics.setShooterSolenoidStatus(true);
    	Timer.delay(0.5);
    	Robot.pneumatics.setShooterSolenoidStatus(false);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	Robot.shooterWheels.spin(0);
    }

    protected void interrupted() {
    }
}