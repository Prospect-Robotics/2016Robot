package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;
import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.HaloDrive;
import org.usfirst.frc2813.Robot2016.commands.ImageProcessing;
import org.usfirst.frc2813.Robot2016.commands.TrajectorySimulator;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends PIDSubsystem {

	private boolean pIDStatus = false;
	private boolean pointedAtGoal = false;
	// private boolean driveStraight = true;
	private double marginOfError = 20;
	private double yaw;
	private double pitch;
	private double previousError = 0;
	private double integral = 0;
	private double newTime = System.currentTimeMillis();
	private double oldTime = 0;
	public static double p = 0.005;
	public static double i = 0.00;
	public static double d = 0.000;

	public DriveTrain() {
		super("ArcadeDrive", p, i, d);
		setAbsoluteTolerance(1e-3);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("ArcadeDrive", "PIDSubsystem Controller",
				getPIDController());
		getPIDController().setOutputRange(-1.0, 1.0);
		enable();
//		setSetpoint(260);
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
	
	public boolean getPointedAtGoal() {
		return pointedAtGoal;
	}
	
	public void setPointedAtGoal(boolean value) {
		pointedAtGoal = value;
	}

	protected double returnPIDInput() {
//		yaw = Robot.nav6.pidGet();
//		pitch = Robot.nav6.getPitch();
//		return yaw;
//		if (Robot.goalValues.length != 0) {
//			return Robot.centerY;
//		} else {
//			System.out.println("Can't find goal.");
//			return getSetpoint();
//		}
		return 0;
	}

	protected void usePIDOutput(double output) {
//		if (Robot.goalValues.length != 0) {
//			if (returnPIDInput() > getSetpoint() - marginOfError &&
//			returnPIDInput() < getSetpoint() + marginOfError) {
//				integral = 0;
//				pointedAtGoal = true;
//				disablePID();
//			} else pointedAtGoal = false;
//				SmartDashboard.putNumber("Nav6UpdateCount", Robot.nav6.getUpdateCount());
//				SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
//				SmartDashboard.putNumber("DriveTrainPIDOutput", output);
//				SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
//				SmartDashboard.putNumber("CurrentSetpoint", getSetpoint());
//			if (pIDStatus) {
//				SmartDashboard.putNumber("PIDMOVE", Robot.oi.getJoystick1().getY());
//				Robot.driveTrain.testDrive(Robot.oi.getJoystick1().getY(), output);
//			}
//		} else {
//			System.out.println("Can't find goal.");
//		}
	}

	public void testDrive(double move, double rotate) {
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
		SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
		RobotMap.driveTrainSpeedControllers.arcadeDrive(-move, -rotate);
		double[] inputs = { -move, -rotate, yaw, pitch };
		Robot.oi.addInputs(inputs);
		enable();
	}

	public void changeSetpoint(double value) {
		value = returnPIDInput() + value;
		if (value > 180) value -= 360;
		else if (value < -180) value += 360;
		setSetpoint(returnPIDInput() + value);
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