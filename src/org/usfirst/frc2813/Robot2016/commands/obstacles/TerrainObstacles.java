package org.usfirst.frc2813.Robot2016.commands.obstacles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TerrainObstacles extends CommandGroup {

    public TerrainObstacles() {
        
    	addSequential(new DriveUntilOnRamp(), 5);
    	addSequential(new DriveUntilOffRamp(), 9);
    	
    }
}
