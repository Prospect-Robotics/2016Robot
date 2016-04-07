package org.usfirst.frc2813.Robot2016.data;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Displays data
 * SD = Smart Dashboard
 * P = Print to Console
 */
public class DataDisplayer {

	private boolean accelerometerDataSD;
	private boolean compressorSatusSD;
	private boolean goalDataSD;
	private boolean joystickDataSD;
	private boolean limitSwitchStatusSD;
	private boolean nav6DataSD;
	private boolean pgEncoderDataSD;
	private boolean shooterAimPIDOutputSD;
	private boolean shooterWheelsDataSD;
	private boolean testSpeedDataSD;
	private boolean ultrasonicDataSD;
	
	public DataDisplayer() {
		
		this.accelerometerDataSD = LogAndDisplaySettings.displayAccelerometerDataSD;
		this.compressorSatusSD = LogAndDisplaySettings.displayCompressorStatusSD;
		this.goalDataSD = LogAndDisplaySettings.displayGoalDataSD;
		this.joystickDataSD = LogAndDisplaySettings.displayJoystickDataSD;
		this.limitSwitchStatusSD = LogAndDisplaySettings.displayLimitSwitchStatusSD;
		this.nav6DataSD = LogAndDisplaySettings.displayNav6DataSD;
		this.pgEncoderDataSD = LogAndDisplaySettings.displayPGEncoderDataSD;
		this.shooterAimPIDOutputSD = LogAndDisplaySettings.displayShooterAimPIDOutputSD;
		this.shooterWheelsDataSD = LogAndDisplaySettings.displayShooterWheelsDataSD;
		this.testSpeedDataSD = LogAndDisplaySettings.displayTestSpeedDataSD;
		this.ultrasonicDataSD = LogAndDisplaySettings.displayUltrasonicDataSD;
		
	}
	
	public void displayData() {

		if (accelerometerDataSD) displayAccelerometerDataSD();
		if (compressorSatusSD) displayCompressorStatusSD();
		if (goalDataSD) displayGoalDataSD();
		if (joystickDataSD) displayJoystickDataSD();
		if (limitSwitchStatusSD) displayLimitSwitchStatusSD();
		if (nav6DataSD) displayNav6DataSD();
		if (pgEncoderDataSD) displayPGEncoderDataSD();
		if (shooterAimPIDOutputSD) displayShooterAimPIDOutputSD();
		if (shooterWheelsDataSD) displayShooterWheelsDataSD();
		if (testSpeedDataSD) displayTestSpeedDataSD();
		if (ultrasonicDataSD) displayUltrasonicDataSD();
		
	}
	
	public void displayAccelerometerDataSD() {

		SmartDashboard.putNumber("Accel_X", RobotMap.accelerometer.getX());
		SmartDashboard.putNumber("Accel_Y", RobotMap.accelerometer.getY());
		SmartDashboard.putNumber("Accel_Z", RobotMap.accelerometer.getZ());
		System.out.println("Accel_Angle" + (88.3 -(Math.atan2(Robot.accelerometerSampling.getYAvg(), Robot.accelerometerSampling.getXAvg()) * 180.0) / Math.PI));
		
	}
	
	public void displayCompressorStatusSD() {
		SmartDashboard.putBoolean("Compressor_Status", Robot.pneumatics.getCompressorStatus());
	}
	
	public void displayGoalDataSD() {

		SmartDashboard.putNumber("Goal_CenerX", Robot.centerX);
		SmartDashboard.putNumber("Goal_CenterY", Robot.centerY);
		SmartDashboard.putBoolean("Goal_Found", true);
		
	}
	
	
	public void displayJoystickDataSD() {

			SmartDashboard.putNumber("Joy1_X", Robot.oi.getJoystick1().getX());
			SmartDashboard.putNumber("Joy1_Y", Robot.oi.getJoystick1().getY());
			SmartDashboard.putNumber("Joy2_X", Robot.oi.getJoystick2().getX());
			SmartDashboard.putNumber("Joy2_Y", Robot.oi.getJoystick2().getY());
		
	}
	
	public void displayLimitSwitchStatusSD() {
		SmartDashboard.putBoolean("LimitSwitch", RobotMap.limitSwitch.get());
	}
	
	public void displayNav6DataSD() {

		SmartDashboard.putNumber("Nav6_MeanPitch", Robot.normalPitch[0]);
		SmartDashboard.putNumber("Nav6_StdDevPitch", Robot.normalPitch[1]);
		
        SmartDashboard.putNumber("IMU_Yaw", Robot.nav6.imu.getYaw());
        SmartDashboard.putNumber("IMU_PIDget", Robot.nav6.imu.pidGet());
        SmartDashboard.putBoolean("IMU_IsCalibrating", Robot.nav6.imu.isCalibrating());
        SmartDashboard.putNumber("IMU_Pitch", Robot.nav6.imu.getPitch());
        SmartDashboard.putNumber("IMU_Roll", Robot.nav6.imu.getRoll());
        SmartDashboard.putNumber("IMU_CompassHeading", Robot.nav6.imu.getCompassHeading());
        SmartDashboard.putNumber("IMU_Update_Count", Robot.nav6.imu.getUpdateCount());
        SmartDashboard.putNumber("IMU_Byte_Count", Robot.nav6.imu.getByteCount());
		
	}
	
	public void displayPGEncoderDataSD() {

		SmartDashboard.putNumber("pgEncoder_Distance", RobotMap.shooterAngleEncoder.getDistance());
		SmartDashboard.putNumber("pgEncoder_Raw", RobotMap.shooterAngleEncoder.getRaw());
		SmartDashboard.putNumber("pgEncoder_Speed", RobotMap.shooterAngleEncoder.get());
		
	}
	
	public void displayShooterAimPIDOutputSD() {
		
		SmartDashboard.putNumber("ShooterAim_pidOutput", Robot.shooterAim.getLastPIDOutput());
		
	}
	
	public void displayShooterWheelsDataSD() {
		
		SmartDashboard.putNumber("ShooterWheels_LeftShooterEncoder", Robot.shooterWheels.returnPIDInputLeft());
		SmartDashboard.putNumber("ShooterWheels_RightShooterEncoder", Robot.shooterWheels.returnPIDInputRight());
		SmartDashboard.putNumber("ShooterWheels_Setpoint", Robot.shooterWheels.getSetpoint());
		
	}
	
	public void displayTestSpeedDataSD() {
		
		SmartDashboard.putNumber("ShooterWheels_TestSpeed", Robot.shooterWheels.getTestSpeed());
		
	}
	
	public void displayUltrasonicDataSD() {

			SmartDashboard.putNumber("Ultrasonic_Raw", Robot.ultrasonicSensor.getValue());
			SmartDashboard.putNumber("Ultrasonic_Volts", Robot.ultrasonicSensor.getVoltage());
			SmartDashboard.putNumber("Ultrasonic_AvgRaw", Robot.ultrasonicSensor.getAverageValue());
			SmartDashboard.putNumber("Ultrasonic_AvgVolts", Robot.ultrasonicSensor.getAverageVoltage());
			SmartDashboard.putNumber("Ultrasonic_Range(In.)", Robot.ultrasonicSensor.getVoltage() / Robot.RANGE_SCALE);
			
	}
	
}
