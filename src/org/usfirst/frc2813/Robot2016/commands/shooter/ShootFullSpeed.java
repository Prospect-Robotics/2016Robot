package org.usfirst.frc2813.Robot2016.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2813.Robot2016.Robot;

public class ShootFullSpeed extends Command {

    public ShootFullSpeed() {
    	requires(Robot.shooterWheels);
    	requires(Robot.pneumatics);
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	Robot.shooterWheels.spin(1);
    	
    	Robot.pneumatics.retractShooterPiston();
    	Timer.delay(1.5);
    	Robot.pneumatics.extendShooterPiston();
    	Timer.delay(0.5);
    	Robot.pneumatics.retractShooterPiston();
    	
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