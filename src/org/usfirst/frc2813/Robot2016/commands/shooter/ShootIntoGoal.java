package org.usfirst.frc2813.Robot2016.commands.shooter;

import org.usfirst.frc2813.Robot2016.commands.driving.RotateRobotToGoal;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ShootIntoGoal extends CommandGroup {
    
    public ShootIntoGoal() {

		// Turn to goal
    	addSequential(new RotateRobotToGoal(), 0.8);
    	addSequential(new WaitCommand(0.2));
    	addSequential(new RotateRobotToGoal(), 0.3);

    	// Aim at goal
    	addSequential(new SetShooterAngleToGoal(), 0.7);
    	addSequential(new ShootFullSpeed());
    	
    }
    
}
