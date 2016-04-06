package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.driving.DirectionalDrive;
import org.usfirst.frc2813.Robot2016.commands.driving.HaloDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	//PID Settings
	private double p;
	private double i;
	private double d;
	private double marginOfError = 2;
	
	private boolean pIDStatus = false;
	private boolean pointedAtGoal = false;
	private double yaw;
	private double pitch;
	private double previousError = 0;
	private double integral = 0;
	private double newTime = System.currentTimeMillis();
	private double oldTime = System.currentTimeMillis();
	private double setpoint = 0;

	public DriveTrain() {
		
		// PID Settings
		p = 0.07;
		i = 0.0;
		d = 0.007;
		
		setSetpoint(0);
		
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

	protected double returnPIDInput() {
		yaw = Robot.nav6.pidGet();
		return yaw;
		
	}
	
	public void customPID() {
		newTime = System.currentTimeMillis();
		double dt = (newTime - oldTime) / 1000;
		oldTime = newTime;
		double error = getSetpoint() - returnPIDInput();
		error = ((error + 180) % 360) - 180;
		integral = integral + error * dt;
		double derivative = (error - previousError) / dt;
		double output = p * error + i * integral + d * derivative;
		previousError = error;
		usePIDOutput(output);
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
		
		if (pIDStatus) {
			Robot.driveTrain.arcadeDrive(0, output);
		}
	}

	public void arcadeDrive(double move, double rotate) {
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput()); // TODO: Move to data displayer
		SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
		RobotMap.driveTrainSpeedControllers.arcadeDrive(-move, -rotate);
		double[] inputs = { -move, -rotate, yaw, pitch};
		Robot.oi.addInputs(inputs);
	}
	
	public void directionalDrive(double x, double y) {
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
		SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
		
		double a = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double b = Math.abs(a / (1 + Math.pow(x / y, 2)) / y);
		double max = Math.max(Math.abs(x), Math.abs(y));
		b = Math.abs(a / (1 + Math.pow(Math.min(Math.abs(x), Math.abs(y)) / max, 2)) / max);
		
		double move = a * b;
		double rotate = Math.toDegrees(Math.atan2(x, y));
		
		if (move > 0.05) setSetpoint(rotate);
		
		double[] normalYaw = Robot.nav6.getNormalizedYaw(10);
		
		SmartDashboard.putNumber("Directional_Power", move);
		SmartDashboard.putNumber("Directional_Rotate", rotate);
		SmartDashboard.putNumber("Directional_Mean", normalYaw[0]);
		SmartDashboard.putNumber("Directional_StdDev", normalYaw[1]);
		
		if (normalYaw[0] < rotate + 3
			&& normalYaw[0] > rotate - 3
			&& normalYaw[1] < 0.5
			&& normalYaw[1] > -0.5) {
			disablePID();
			Robot.driveTrain.arcadeDrive(move, 0);
		} else
			enablePID();
		
	}

	public void modifySetpoint(double angleToAdd) {
		angleToAdd = returnPIDInput() + angleToAdd;
		angleToAdd = ((angleToAdd + 180) % 360) - 180;
		setSetpoint(angleToAdd);
	}

}