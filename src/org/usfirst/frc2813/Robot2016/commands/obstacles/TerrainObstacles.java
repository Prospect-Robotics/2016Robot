package org.usfirst.frc2813.Robot2016.commands.obstacles;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.subsystems.TiltCameraDistanceCalculator;
import org.usfirst.frc2813.Robot2016.commands.shooter.ShootFullSpeed;
import org.usfirst.frc2813.Robot2016.commands.shooter.SetShooterAngleToGoal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TerrainObstacles extends Command {

    public TerrainObstacles() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	while (!ObstacleHelper.isOnRamp()) {
    		Robot.driveTrain.arcadeDrive(-0.82, 0);
    	}
    	
    	while (ObstacleHelper.isOnRamp()) {
    		Robot.driveTrain.arcadeDrive(-0.82, 0);
    	}
    	
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
