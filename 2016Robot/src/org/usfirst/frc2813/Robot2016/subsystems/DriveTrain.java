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

	public DriveTrain() {
		super("ArcadeDrive", 0.05, 0.0, 0.0);
		setAbsoluteTolerance(0);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("ArcadeDrive", "PIDSubsystem Controller", getPIDController());
		getPIDController().setOutputRange(-1.0, 1.0);
		enable();
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
		SmartDashboard.putNumber("DriveTrainPIDInput", returnPIDInput());
		SmartDashboard.putNumber("DriveTrainPIDOutput", output);
		SmartDashboard.putString("PIDStatus", String.valueOf(pIDStatus));
		SmartDashboard.putNumber("CurrentSetpoint", getSetpoint());
//		if (pIDStatus) {
//			Robot.driveTrain.testDrive(Robot.oi.getJoystick1().getY(), output);
//		}
	}
	
	public void testDrive(double move, double rotate) {
		returnPIDInput();
		RobotMap.driveTrainSpeedControllers.arcadeDrive(-move, -rotate);
		double[] inputs = {-move, -rotate, yaw, pitch};
		Robot.oi.addInputs(inputs);
	}

	public double getOldSetpoint() {
		return getSetpoint();
	}

	public void setNewSetpoint(double value) {
		setSetpoint(value);
	}
}