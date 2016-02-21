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
    	addSequential(new FirePistonShooter());
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    	Robot.shooterWheels.spin(0);
    }
}