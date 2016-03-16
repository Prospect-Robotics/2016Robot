package org.usfirst.frc2813.Robot2016.commands.shooter;

import org.usfirst.frc2813.Robot2016.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootIntoGoal extends CommandGroup {
    
    public ShootIntoGoal() {
		System.out.println("hi1");
    	
    	if (Robot.goalValues.length != 0) {
    		System.out.println("hi2");
//        	addSequential(new AngleRobotToGoal());
    		
	    	Robot.autoShooterValues = TrajectorySimulator.findTrajectory();
	    	
	    	if (Robot.autoShooterValues.length != 0) {
//	    		new AngleRobotToGoal();
//	    		while (!Robot.driveTrain.getPointedAtGoal()) {};
		    	addSequential(new SetShooterAngleToGoal());
		    	addSequential(new ManualShoot());
	    	}
	    	
    	} else {
    		
    		System.out.println("Goal not found, aborted auto shooting.");
    		
    	}
    }
}
