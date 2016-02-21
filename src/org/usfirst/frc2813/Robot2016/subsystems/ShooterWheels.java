package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.IdleShooter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterWheels extends PIDSubsystem {

	private final SpeedController speedControllerLeft = RobotMap.shooterSpeedControllerLeft;
	private final SpeedController speedControllerRight = RobotMap.shooterSpeedControllerRight;
	private final Encoder encoder = RobotMap.shooterEncoder;
	
	private boolean pIDStatus = false;
	private double previousError = 0;
	private double integral = 0;
	private double newTime = System.currentTimeMillis();
	private double oldTime = 0;
	public static double p = 0.08;
	public static double i = 0.012;
	public static double d = 0.007;

	public ShooterWheels() {
		super("Shoot", 1.0, 0.0, 0.0);
		setAbsoluteTolerance(0.2);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("Shoot", "PIDSubsystem Controller",
				getPIDController());
		getPIDController().setOutputRange(-1.0, 1.0);
		disable();
	}

	public void enablePID() {
		pIDStatus = true;
	}
	
	public void disablePID() {
		pIDStatus = false;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new IdleShooter());
	}

	protected double returnPIDInput() {
		return encoder.pidGet();
	}

	protected void usePIDOutput(double output) {
		if (pIDStatus) {
			speedControllerLeft.pidWrite(output);
			speedControllerRight.pidWrite(output);
		}
	}

	public void setNewSetpoint(double setpoint) {
		setSetpoint(setpoint);
	}
	
	public void spin(double speed) {
		speedControllerLeft.set(-speed);
		speedControllerRight.set(-speed);
	}

	public void customPID(double p, double i, double d) {
		double dt = (newTime - oldTime) / 1000;
		oldTime = newTime;
		double error = getSetpoint() - returnPIDInput();
//		if (error > 180) error -= 360;
//		else if (error < -180) error += 360;
		integral = integral + error * dt;
		double derivative = (error - previousError) / dt;
		double output = p * error + i * integral + d * derivative;
		previousError = error;
		newTime = System.currentTimeMillis();
		usePIDOutput(output);
	}
	
}