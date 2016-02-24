package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class AccelerometerSampling extends Subsystem {

	final int numberOfSamples = 10;
	
	private ADXL345_I2C accelerometer;
	private double[] xValues;
	private double[] yValues;
	private double[] zValues;
	private int errors = 0; //TEMP
	
	public AccelerometerSampling() {
		accelerometer = RobotMap.accelerometer;
		xValues = new double[numberOfSamples];
		yValues = new double[numberOfSamples];
		zValues = new double[numberOfSamples];
		for (int i = 0; i < numberOfSamples; i++) {
			update();
		}
	}
	
	protected void initDefaultCommand() {
		
	}
	
	public void update(){
		for (int i = 0; i < numberOfSamples - 1; i++) {
			xValues[i] = xValues[i + 1];
			yValues[i] = yValues[i + 1];
			zValues[i] = zValues[i + 1];
		}
		xValues[numberOfSamples - 1] = this.getX();
		yValues[numberOfSamples - 1] = this.getY();
		zValues[numberOfSamples - 1] = this.getZ();
		if (xValues[numberOfSamples - 1] == 0 && yValues[numberOfSamples - 1] == 0 && zValues[numberOfSamples - 1] == 0) {
			errors++;
			Robot.shooterAim.setAngle(Robot.shooterAim.getAngle());
			accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
			System.out.println(errors);
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

