package org.usfirst.frc2813.Robot2016.commands.shooter;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterOuttake extends Command {

    public ShooterOuttake() {
		requires(Robot.shooterWheels);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooterWheels.enableController();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//		Robot.shooterWheels.spin(-0.45);
    	Robot.shooterWheels.setSetpoint(54000);
//    	double timer = System.currentTimeMillis();
//    	while (!Robot.shooterWheels.getShooterSpeedSet()) {
//    		if (System.currentTimeMillis() > timer + 2000) break;
//    	}
//    	Robot.pneumatics.extendShooterPiston();
//    	Timer.delay(0.5);
//    	Robot.pneumatics.retractShooterPiston();
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
