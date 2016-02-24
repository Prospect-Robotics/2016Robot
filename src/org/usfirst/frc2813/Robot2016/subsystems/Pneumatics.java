package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {

	private final Compressor compressor = RobotMap.compressor;
	private final Solenoid shooterSolenoidFirstChannel = RobotMap.shooterSolenoidFirstChannel;
	private final Solenoid shooterSolenoidSecondChannel = RobotMap.shooterSolenoidSecondChannel;
//	private final Solenoid elevatorSolenoid = RobotMap.elevatorSolenoid;
	
	public void initDefaultCommand() {
		
	}
	
	public boolean getCompressorStatus() {
		return compressor.enabled();
	}
	
//	public boolean getElevatorSolenoidStatus() {
//		return elevatorSolenoid.get();
//	}
	
	public void setCompressor(boolean value) {
		if (value) compressor.start();
		else compressor.stop();
	}
	
//	public void setElevatorSolenoidStatus(boolean value) {
//		elevatorSolenoid.set(value);
//	}
	
	public void extendShooterPiston() {
		shooterSolenoidFirstChannel.set(true);
		shooterSolenoidSecondChannel.set(false);
	}
	
	public void retractShooterPiston() {
		shooterSolenoidFirstChannel.set(false);
		shooterSolenoidSecondChannel.set(true);
	}
	
}