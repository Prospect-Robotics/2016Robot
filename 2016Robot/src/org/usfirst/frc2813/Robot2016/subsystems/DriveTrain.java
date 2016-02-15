package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.HaloDrive;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends PIDSubsystem {

	private boolean pIDStatus = true;
	// private boolean driveStraight = true;
	private double marginOfError = 2;
	private double yaw;
	private double pitch;
	private double previousError = 0;
	private double integral = 0;
	private double newTime = System.currentTimeMillis();
	private double oldTime = 0;
	public static double p = 0.09;
	public static double i = 0.0;
	public static double d = 0.0;

	public DriveTrain() {
		super("ArcadeDrive", p, i, d);
		setAbsoluteTolerance(1e-3);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("ArcadeDrive", "PIDSubsystem Controller",
				getPIDController());
		getPIDController().setOutputRange(-1.0, 1.0);
		enable();
		setSetpoint(0);
	}

	public void initDefaultCommand() {
//		setDefaultCommand(new HaloDrive());
	}

	public boolean getPIDStatus() {
		return pIDStatus;
	}

	public void togglePID() {
		pIDStatus = !pIDStatus;
	}

	public void disablePID() {
		pIDStatus = false;
	}

	public void enablePID() {
		pIDStatus = true;
	}

	protected double returnPIDInput() {
		yaw = Robot.nav6.pidGet();
		pitch = Robot.nav6.getPitch();
		return yaw;
	}

	protected void usePIDOutput(double output) {
		// if (returnPIDInput() > getSetpoint() - marginOfError &&
		// returnPIDInput() < getSetpoint() + marginOfError) {
		// disablePID();
		// } else {
		// enablePID();
		// }
		SmartDashboard
				.putNumber("Nav6UpdateCount", Robot.nav6.getUpdateCount());
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
		SmartDashboard.putNumber("DriveTrainPIDOutput", output);
		SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
		SmartDashboard.putNumber("CurrentSetpoint", getSetpoint());
		if (pIDStatus) {
			SmartDashboard.putNumber("PIDMOVE", Robot.oi.getJoystick1().getY());
			Robot.driveTrain.testDrive(Robot.oi.getJoystick1().getY(), output);
		}
	}

	public void testDrive(double move, double rotate) {
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
		SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
		RobotMap.driveTrainSpeedControllers.arcadeDrive(-move, -rotate);
		double[] inputs = { -move, -rotate, yaw, pitch };
		Robot.oi.addInputs(inputs);
		enable();
	}

	public double getOldSetpoint() {
		return getSetpoint();
	}

	public void setNewSetpoint(double value) {
		setSetpoint(value);
	}

	public void customPID(double p, double i, double d) {
		double dt = newTime - oldTime;
		oldTime = newTime;
		double error = getSetpoint() - returnPIDInput();
		integral = integral + error * dt;
		double derivative = (error - previousError) / dt;
		double output = p * error + i * integral + d * derivative;
		previousError = error;
		newTime = System.currentTimeMillis();
		usePIDOutput(output);
	}
}