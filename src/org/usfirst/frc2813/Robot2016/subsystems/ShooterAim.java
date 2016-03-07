package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.shooter.IdleShooterAngle;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterAim extends Subsystem {

		
	private int sensorSelection; // Sensor setting
	private boolean pIDStatus; // PID is enabled when true
	private double marginOfError; // The amount of degrees we are willing to be off target
	private double setpoint; // This is the value we want to get the sensor at -- the goal of the PID loop

	// Angle limit for shooter (in degrees)
	private double lowerLimit;
	private double upperLimit;
	
	private boolean shooterSet; // True when shooter is at the desired location (within the margin of error)
	
	// These variables are for the custom PID loop to work
	private double newTime;
	private double oldTime;
	private double integral;
	private double previousError;
	
	// Hardware declarations
	private final Encoder encoder = RobotMap.shooterEncoder;;
	private final ADXL345_I2C accelerometer = RobotMap.accelerometer;
	private final DigitalInput limitSwitch = RobotMap.limitSwitch;
	private final SpeedController speedControllerAngle = RobotMap.shooterSpeedControllerAngle;
	
	// Sensor selections
	private final int ENCODER = 0; // Encoder is more reliable, not heavily affected by impact
	private final int ACCELEROMETER = 1; // Accelerometer values are drastically affected by impact.
										  // Cannot function properly when forces other than gravity are acting upon it


	public ShooterAim() {
		
		// PID settings
		sensorSelection = ENCODER; // Default sensor is encoder
		pIDStatus = true; // PID is on by default
		marginOfError = 0.8; // A good margin of error is 0.8 of a degree
		setAngle(0); // Set angle to 0 by default
		
		// Shooter angle limits | TODO: Actually measure
		lowerLimit = -32;
		upperLimit = 60;

		shooterSet = false; // Assume shooter not set at desired value by default
		
		// PID variable initiations
		integral = 0;
		previousError = 0;
		oldTime = System.currentTimeMillis();
		
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new IdleShooterAngle());
	}

	// These accessors are how the other classes interface with this one 
	public void enablePID() {
		pIDStatus = true;
	}
	
	public void disablePID() {
		pIDStatus = false;
	}

	public boolean getPointedAtGoal() {
		return shooterSet;
	}
	
	public void setPointedAtGoal(boolean value) {
		shooterSet = value;
	}
	
	public double getAngle() {
		return getSetpoint();
	}
	
	public void setAngle(double angle) {
		if (angle < lowerLimit) angle = lowerLimit;
		else if (angle > upperLimit) angle = upperLimit;
		setSetpoint(angle);
	}
	
	protected double getSetpoint() {
		return setpoint;
	}
	
	protected void setSetpoint(double setpoint) {
		this.setpoint  = setpoint;
	}

	// We use a custom PID loop as opposed to the built-in PID subsystem because it is more customizable and easier to diagnose
	// This is called about once every 20ms in the Robot class
	public void customPID(double p, double i, double d) {
		
		newTime = System.currentTimeMillis();
		double dt = (newTime - oldTime) / 1000; // The change in time is calculated then converted to seconds
		oldTime = newTime;
		
		double error = getSetpoint() - returnPIDInput(); // The error is how much the shooter is off from the desired angle
		integral = integral + error * dt;
		double derivative = (error - previousError) / dt;
		
		double output = p * error + i * integral + d * derivative; // Output is calculated by taking settings as well as measurements into account
		
		previousError = error;
		
		// Limit output to be in the range of -1 (motor 100% power in reverse direction) to 1 (motor 100% power in forward direction)
		if (output > 1) output = 1;
		else if (output < -1) output = -1;
		
		usePIDOutput(output);
		
	}

	// This is what the PID loop calls to actually use the value that it calculated
	protected void usePIDOutput(double output) {
		if (returnPIDInput() > getSetpoint() - marginOfError &&
		returnPIDInput() < getSetpoint() + marginOfError) {
			integral = 0;
			shooterSet = true;
		} else shooterSet = false;
		SmartDashboard.putNumber("AimPIDOutput", output);
		SmartDashboard.putNumber("AimPIDSetpoint", getAngle());
		if (pIDStatus) {
			speedControllerAngle.pidWrite(output); // This is the most important line in this method
													// It gives the calculated motor value to the motor
		}
	}

	protected double returnPIDInput() {
		if (sensorSelection == ENCODER) return (encoder.getDistance()); // TODO: Replace with linear model
		if (sensorSelection == ACCELEROMETER) return 88.3 - (Math.atan2(Robot.avgAccel.getYAvg(), Robot.avgAccel.getXAvg()) * 180 / Math.PI);
		return 0;
	}
	
}