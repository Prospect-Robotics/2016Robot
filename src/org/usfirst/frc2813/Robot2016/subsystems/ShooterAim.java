package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterAim extends Subsystem {

	// PID Settings
	private double p;
	private double i;
	private double d;
	private int sensorSelection; // Sensor setting
	private boolean pIDStatus; // PID is enabled when true
	private double setpoint; // This is the value we want to get the sensor at -- the goal of the PID loop
	private double marginOfError; // The amount of degrees we are willing to be off target

	// Angle limit for shooter (in degrees)
	private double lowerLimit;
	private double upperLimit;
	
	private boolean shooterAngleSet; // True when shooter is at the desired location (within the margin of error)
	
	// Storage of PID outputs
	private int numberOfOutputs; // Number of outputs to store
	private double[] pidOutputs; // The last outputs the pid loop gave
	
	// These variables are for the custom PID loop to work
	private double newTime;
	private double oldTime;
	private double integral;
	private double previousError;
	
	// Hardware declarations
	private final Encoder encoder;
	private final SpeedController speedControllerAngle;
	
	// Sensor selections
	private final int ENCODER = 0; // Encoder is more reliable, not heavily affected by impact
	private final int ACCELEROMETER = 1; // Accelerometer values are drastically affected by impact.
										  // Cannot function properly when forces other than gravity are acting upon it


	public ShooterAim() {
		
		// PID settings
		p = 0.014;
		i = 0.0;
		d = 0.0014;
		sensorSelection = ACCELEROMETER; // Default sensor is encoder
		pIDStatus = false; // PID is on by default
		setSetpoint(0); // Set angle to 0 by default
		marginOfError = 2; // A good margin of error is 2 degrees
		
		// Shooter angle limits | TODO: Actually measure
		lowerLimit = -32;
		upperLimit = 60;

		shooterAngleSet = false; // Assume shooter angle not set at desired value by default
		
		numberOfOutputs = 10;
		pidOutputs = new double[numberOfOutputs];
		
		// PID variable initiations
		integral = 0;
		previousError = 0;
		oldTime = System.currentTimeMillis();
		
		// Hardware initializations
		encoder = RobotMap.shooterAngleEncoder;
		speedControllerAngle = RobotMap.shooterSpeedControllerAngle;
		
	}
	
	public void initDefaultCommand() {
	}

	// These accessors allow other classes to interface with this one 
	public void enablePID() {
		pIDStatus = true;
	}
	
	public void disablePID() {
		pIDStatus = false;
	}
	
	public double getLastPIDOutput() {
		return pidOutputs[numberOfOutputs - 1];
	}

	public boolean isPointedAtGoal() {
		return shooterAngleSet;
	}
	
	public void manualAim(double value) {
		
		if (Robot.pneumatics.isArmExtended() || returnPIDInput() < -4 || value < 0)
			speedControllerAngle.set(value);
		else
			speedControllerAngle.set(0);
		
	}
	
	public void modifySetpoint(double amountToAdd) {
		setSetpoint(getSetpoint() + amountToAdd);
	}
	
	protected double getSetpoint() {
		return setpoint;
	}
	
	public void setSetpoint(double setpoint) {
		if (setpoint < lowerLimit) setpoint = lowerLimit - 1;
		else if (setpoint > upperLimit) setpoint = upperLimit + 1;
		this.setpoint = setpoint;
	}

	// We use a custom PID loop as opposed to the built-in PID subsystem because it is more customizable and easier to diagnose
	// This is called about once every 20ms in the Robot class
	public void customPID() {
		
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
		
		updatePIDOutputArray(output);
		usePIDOutput(output);
		
	}

	// This is what the PID loop calls to actually use the value that it calculated
	protected void usePIDOutput(double output) {
		
		if (returnPIDInput() > getSetpoint() - marginOfError &&
		returnPIDInput() < getSetpoint() + marginOfError) {
			integral = 0;
			shooterAngleSet = true;
		} else shooterAngleSet = false;

		SmartDashboard.putNumber("AimPIDInput", returnPIDInput());
		SmartDashboard.putNumber("AimPIDOutput", output);
		SmartDashboard.putNumber("AimPIDSetpoint", getSetpoint());
		
		if (pIDStatus && Robot.pneumatics.isArmExtended()) {
			speedControllerAngle.pidWrite(output); // This is the most important line in this method
													// It gives the calculated motor value to the motor
		} else if (pIDStatus && !Robot.pneumatics.isArmExtended()) {
			speedControllerAngle.pidWrite(0);
		}
		
	}
	
	protected void updatePIDOutputArray(double output) {

		// Shift all of the values to the left once
		for (int i = 0; i < numberOfOutputs - 1; i++) {
			pidOutputs[i] = pidOutputs[i + 1];
		}
		
		
		// Update all of the values with accelerometer
		pidOutputs[numberOfOutputs - 1] = output;
		
	}

	protected double returnPIDInput() {
		if (sensorSelection == ENCODER) return (encoder.getDistance()); // TODO: Replace with linear model
		if (sensorSelection == ACCELEROMETER) return Robot.accelerometerSampling.getAngleToGround();
		return 0;
	}
	
}