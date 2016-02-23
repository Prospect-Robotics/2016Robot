package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.IdleShooterAngle;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterAim extends PIDSubsystem {

	private final SpeedController speedControllerAngle = RobotMap.shooterSpeedControllerAngle;
	private final ADXL345_I2C accelerometer = RobotMap.accelerometer;
	private final Encoder encoder = RobotMap.shooterEncoder;
	private final DigitalInput limitSwitch = RobotMap.limitSwitch;
	
	private boolean pIDStatus = true;
	private boolean pointedAtGoal = false;
	private double marginOfError = 0.3;
	private double previousError = 0;
	private double integral = 0;
	private double motorSpeed = 0.25;
	private double newTime;
	private double oldTime = System.currentTimeMillis();

	public ShooterAim() {
		super("Shoot", 0.000, 0.00, 0.0);
		setAbsoluteTolerance(0.1);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("Shoot", "PIDSubsystem Controller",
				getPIDController());
		getPIDController().setOutputRange(-1.0, 1.0);
		enable();
		setAngle(0);
	}

	public void enablePID() {
		pIDStatus = true;
	}
	
	public void disablePID() {
		pIDStatus = false;
	}

	public boolean getPointedAtGoal() {
		return pointedAtGoal;
	}
	
	public void setPointedAtGoal(boolean value) {
		pointedAtGoal = value;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new IdleShooterAngle());
	}

	protected double returnPIDInput() {
		return 88.3 - (Math.atan2(Robot.avgAccel.getYAvg(), Robot.avgAccel.getXAvg()) * 180 / Math.PI);
	}

	protected void usePIDOutput(double output) {
		if (returnPIDInput() > getSetpoint() - marginOfError &&
		returnPIDInput() < getSetpoint() + marginOfError) {
			integral = 0;
			pointedAtGoal = true;
		} else pointedAtGoal = false;
//		SmartDashboard.putNumber("AimPIDOutput", output);
		SmartDashboard.putNumber("AimPIDSetpoint", getAngle());
		if (pIDStatus) {
			speedControllerAngle.pidWrite(output);
		}
	}
	
	public double getAngle() {
		return getSetpoint();
	}
	
	public void setAngle(double angle) {
		setSetpoint(angle);
	}

	public void customPID(double p, double i, double d) {
		newTime = System.currentTimeMillis();
		double dt = (newTime - oldTime) / 1000;
		oldTime = newTime;
		double error = getSetpoint() - returnPIDInput();
		integral = integral + error * dt;
		double derivative = (error - previousError) / dt;
		double output = p * error + i * integral + d * derivative;
		previousError = error;
		if (output > 1) output = 1;
		else if (output < -1) output = -1;
		usePIDOutput(output);
	}
	
}