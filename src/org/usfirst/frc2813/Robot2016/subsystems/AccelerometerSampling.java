package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

// This class helps the ShooterAim class use the right values for PID input.
public class AccelerometerSampling {

	final int numberOfSamples = 10;
	
	private ADXL345_I2C accelerometer;
	private double[] xValues;
	private double[] yValues;
	private double[] zValues;
	
	public AccelerometerSampling() {
		accelerometer = RobotMap.accelerometer;
		xValues = new double[numberOfSamples];
		yValues = new double[numberOfSamples];
		zValues = new double[numberOfSamples];
		for (int i = 0; i < numberOfSamples; i++) {
			update();
		}
	}
	
	public double getAngleToGround() {
		return 88.3 - (Math.atan2(getYAvg(), getXAvg()) * 180 / Math.PI);
	}
	
	public void update(){
		
		// Shift all of the values to the left once
		for (int i = 0; i < numberOfSamples - 1; i++) {
			xValues[i] = xValues[i + 1];
			yValues[i] = yValues[i + 1];
			zValues[i] = zValues[i + 1];
		}
		
		// Update all of the values with accelerometer
		xValues[numberOfSamples - 1] = this.getX();
		yValues[numberOfSamples - 1] = this.getY();
		zValues[numberOfSamples - 1] = this.getZ();
		
		// If all of the latest values are 0, then the accelerometer crashed. DisablePID and stop the motors, then try to re-initialize it.
		if (xValues[numberOfSamples - 1] == 0 && yValues[numberOfSamples - 1] == 0 && zValues[numberOfSamples - 1] == 0) {
			
			Robot.shooterAim.disablePID();
			Robot.shooterAim.manualAim(0);
			
			accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
			
		}
		
	}
	
	public double getXAvg() {
		double sum = 0;
		for (int i = 0; i < numberOfSamples; i++) {
			sum += xValues[i];
		}
		return sum/numberOfSamples;
	}
	
	public double getYAvg() {
		double sum = 0;
		for (int i = 0; i < numberOfSamples; i++) {
			sum += yValues[i];
		}
		return sum/numberOfSamples;
	}
	
	public double getZAvg() {
		double sum = 0;
		for (int i = 0; i < numberOfSamples; i++) {
			sum += zValues[i];
		}
		return sum/numberOfSamples;
	}
	
	private double getX(){
		return RobotMap.accelerometer.getX();
	}
	
	private double getY(){
		return RobotMap.accelerometer.getY();
	}
	
	private double getZ(){
		return RobotMap.accelerometer.getZ();
	}
	
}

