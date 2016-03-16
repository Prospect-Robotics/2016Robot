package org.usfirst.frc2813.Robot2016.commands.obstacles;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TerrainObstaclesFast extends Command {

    public TerrainObstaclesFast() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	while (!ObstacleHelper.isOnRamp()) {
    		Robot.driveTrain.arcadeDrive(-1, 0);
    		Robot.nav6.updatePitch();
    		Robot.normalPitch = Robot.nav6.getNormalizedPitch(10);
    		SmartDashboard.putNumber("MeanPitch", Robot.normalPitch[0]);
    		SmartDashboard.putNumber("StdDevPitch", Robot.normalPitch[1]);
    	}
    	while (ObstacleHelper.isOnRamp()) {
    		Robot.driveTrain.arcadeDrive(-1, 0);
    		Robot.nav6.updatePitch();
    		Robot.normalPitch = Robot.nav6.getNormalizedPitch(10);
    		SmartDashboard.putNumber("MeanPitch", Robot.normalPitch[0]);
    		SmartDashboard.putNumber("StdDevPitch", Robot.normalPitch[1]);
    	}
//    	Robot.driveTrain.arcadeDrive(-0.7, 0);
//    	Timer.delay(20000);
    	Robot.driveTrain.arcadeDrive(0, 0);
    	
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
