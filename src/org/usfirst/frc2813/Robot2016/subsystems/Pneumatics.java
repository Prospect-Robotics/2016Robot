package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {

	private final Compressor compressor = RobotMap.compressor;
	private final Solenoid shooterSolenoid = RobotMap.shooterSolenoid;
	private final Solenoid elevatorSolenoid = RobotMap.elevatorSolenoid;
	private final Solenoid solenoid3 = RobotMap.solenoid3;
	private final Solenoid solenoid4 = RobotMap.solenoid4;
	private final Solenoid solenoid5 = RobotMap.solenoid5;
	
	public void initDefaultCommand() {
		
	}
	
	public boolean getCompressorStatus() {
		return compressor.enabled();
	}
	
	public boolean getShooterSolenoidStatus() {
		return shooterSolenoid.get();
	}
	
	public boolean getElevatorSolenoidStatus() {
		return elevatorSolenoid.get();
	}

	public boolean getSolenoid3Status() {
		return solenoid3.get();
	}
	
	public boolean getSolenoid4Status() {
		return solenoid4.get();
	}
	
	public boolean getSolenoid5Status() {
		return solenoid5.get();
	}
	
	public void setCompressor(boolean value) {
		if (value) compressor.start();
		else compressor.stop();
	}
	
	public void setShooterSolenoidStatus(boolean value) {
		shooterSolenoid.set(value);
	}
	
	public void setElevatorSolenoidStatus(boolean value) {
		elevatorSolenoid.set(value);
	}
	
	public void setSolenoid3Status(boolean value) {
		solenoid3.set(value);
	}
	
	public void setSolenoid4Status(boolean value) {
		solenoid4.set(value);
	}
	
	public void setSolenoid5Status(boolean value) {
		solenoid5.set(value);
	}
	
}