package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.driving.HaloDrive;
import org.usfirst.frc2813.Robot2016.commands.shooter.ImageProcessing;
import org.usfirst.frc2813.Robot2016.commands.shooter.TrajectorySimulator;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	private boolean pIDStatus = false;
	private boolean pointedAtGoal = false;
	private double marginOfError = 2;
	private double yaw;
	private double pitch;
	private double previousError = 0;
	private double integral = 0;
	private double newTime = System.currentTimeMillis();
	private double oldTime = System.currentTimeMillis();
	private double setpoint = 0;
	// private boolean driveStraight = true;

	public DriveTrain() {
		setSetpoint(0);
//		setSetpoint(260);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new HaloDrive());
	}

	public boolean getPIDStatus() {
		return pIDStatus;
	}

	public void togglePID() {
		pIDStatus = !pIDStatus;
	}

	public void enablePID() {
		pIDStatus = true;
	}

	public void disablePID() {
		pIDStatus = false;
	}
	
	public double getSetpoint() {
		return setpoint;
	}
	
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}
	
	public boolean getPointedAtGoal() {
		return pointedAtGoal;
	}
	
	public void setPointedAtGoal(boolean value) {
		pointedAtGoal = value;
	}

	protected double returnPIDInput() {
//		return 0;
		yaw = Robot.nav6.pidGet();
		return yaw;
		
	}

	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("Nav6UpdateCount", Robot.nav6.getUpdateCount());
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
		SmartDashboard.putNumber("DriveTrainPIDOutput", output);
		SmartDashboard.putString("DriveTrainPIDStatus", String.valueOf(pIDStatus));
		SmartDashboard.putNumber("DriveTrainCurrentSetpoint", getSetpoint());
		
		if (returnPIDInput() > getSetpoint() - marginOfError &&
		returnPIDInput() < getSetpoint() + marginOfError) {
			integral = 0;
			pointedAtGoal = true;
			disablePID();
		} else pointedAtGoal = false;
		
		if (Robot.goalValues.length != 0) {
			if (pIDStatus) {
				Robot.driveTrain.arcadeDrive(0, output);
			}
		} else {
			disablePID();
		}
	}

	public void arcadeDrive(double move, double rotate) {
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
		SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
		RobotMap.driveTrainSpeedControllers.arcadeDrive(-move, -rotate);
		double[] inputs = { -move, -rotate, yaw, pitch};
		Robot.oi.addInputs(inputs);
	}

	public void changeSetpoint(double value) {
		value = returnPIDInput() + value;
		if (value > 180) value -= 360;
		else if (value < -180) value += 360;
		setSetpoint(value);
	}

	public void customPID(double p, double i, double d) {
		newTime = System.currentTimeMillis();
		double dt = (newTime - oldTime) / 1000;
		oldTime = newTime;
		double error = getSetpoint() - returnPIDInput();
		if (error > 180) error -= 360;
		else if (error < -180) error += 360;
		integral = integral + error * dt;
		double derivative = (error - previousError) / dt;
		double output = p * error + i * integral + d * derivative;
		previousError = error;
		usePIDOutput(output);
	}
}