package org.usfirst.frc2813.Robot2016.commands;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootIntoGoal extends CommandGroup {
    
    public  ShootIntoGoal() {
    	
    	if (Robot.goalValues.length != 0) {
    		
//        	addSequential(new AngleRobotToGoal());
    		
	    	Robot.autoShooterValues = TrajectorySimulator.findTrajectory();
	    	
	    	if (Robot.autoShooterValues.length != 0) {
		    	addSequential(new SetShooterAngleToGoal());
		    	addSequential(new Shoot());
	    	}
	    	
    	} else {
    		
    		System.out.println("Goal not found, aborted auto shooting.");
    		
    	}
    }
}
