package org.usfirst.frc2813.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class Shooter extends Command {

    public Shooter() {
    	requires(Robot.shooter);
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	Robot.shooter.move(1);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    	Robot.shooter.move(0);
    }
}