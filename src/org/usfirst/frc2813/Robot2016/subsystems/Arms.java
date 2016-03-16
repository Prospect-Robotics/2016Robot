package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Arms extends Subsystem {
	
	private final SpeedController speedControllerTilt = RobotMap.armsSpeedControllerTilt;
	private final SpeedController speedControllerIntake = RobotMap.armsSpeedControllerIntake;

//	private final AnalogPotentiometer potentiometer = RobotMap.armsPotentiometer;

	public Arms() {
		
	}

	public void initDefaultCommand() {

	}

	protected double returnPIDInput() {
//		return potentiometer.get();
		return 0;
	}

	protected void usePIDOutput(double output) {
		
	}

	public void move(double move) {
		speedControllerTilt.set(-move);
	}

	public void intake(double move) {
		speedControllerIntake.set(move);
	}
}