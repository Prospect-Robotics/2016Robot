package org.usfirst.frc2813.Robot2016.commands.obstacles;

import org.usfirst.frc2813.Robot2016.commands.driving.TurnToOpponentAlliance;
import org.usfirst.frc2813.Robot2016.commands.driving.TurnToOurAlliance;
import org.usfirst.frc2813.Robot2016.commands.shooter.ShootIntoGoal;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ObstacleHighGoalAndReset extends CommandGroup {

	public ObstacleHighGoalAndReset() {
		
		addSequential(new TerrainObstacles());
		addSequential(new ShootIntoGoal());
		addSequential(new TurnToOurAlliance(), 3);
		addSequential(new TerrainObstacles());
		addSequential(new TurnToOpponentAlliance(), 3);
		
	}
	
}
