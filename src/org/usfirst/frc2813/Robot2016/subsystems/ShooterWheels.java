package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.shooter.IdleShooterWheels;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterWheels extends Subsystem {

	private boolean controllerStatus; // PID is enabled when true
	private double setpoint; // This is the value we want to get the sensor at -- the goal of the PID loop
	private double marginOfError; // Our target range of speed (in cm/s)

	private double testSpeed; // Speed that wheels spin when testing is enabled

	private boolean shooterSpeedSet; // True when shooter wheels are spinning at the same speed
	
	// Hardware declarations
	private final SpeedController speedControllerLeft;
	private final SpeedController speedControllerRight;
	private final Encoder leftEncoder;
	private final Encoder rightEncoder;

	public ShooterWheels() {

		// Bang-bang controller settings
		controllerStatus = true; // PID is on by default
		setSetpoint(0);
		marginOfError = 1000; // A good margin of error is 1000

		testSpeed = 0;
		
		shooterSpeedSet = false; // Assume shooter wheels not spinning at desired speed
		
		// Hardware initializations
		speedControllerLeft = RobotMap.shooterSpeedControllerLeft;
		speedControllerRight = RobotMap.shooterSpeedControllerRight;
		leftEncoder = RobotMap.shooterSpeedEncoderLeft;
		rightEncoder = RobotMap.shooterSpeedEncoderRight;
		
	}

	public void initDefaultCommand() {
		setDefaultCommand(new IdleShooterWheels());
	}

	public void enableController() {
		controllerStatus = true;
	}
	
	public void disableController() {
		controllerStatus = false;
	}
	
	public boolean getShooterSpeedSet() {
		return shooterSpeedSet;
	}

	public double getTestSpeed() {
		return testSpeed;
	}

	public void setTestSpeed(double testSpeed) {
		this.testSpeed = testSpeed;
	}
	
	public double getSetpoint() {
		return setpoint;
	}
	
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public double returnPIDInputLeft() {
		return leftEncoder.pidGet();
	}

	public double returnPIDInputRight() {
		return rightEncoder.pidGet();
	}

	protected void useControllerOutput(double[] output) {
		
		if (returnPIDInputLeft() > getSetpoint() - marginOfError &&
		returnPIDInputLeft() < getSetpoint() + marginOfError &&
		returnPIDInputRight()  > getSetpoint() - marginOfError &&
		returnPIDInputRight() < getSetpoint() + marginOfError) {
			shooterSpeedSet = true;
		} else shooterSpeedSet = false;
		
		SmartDashboard.putNumber("LeftShooterEncoder", returnPIDInputLeft());
		SmartDashboard.putNumber("RightShooterEncoder", returnPIDInputRight());
		SmartDashboard.putNumber("ShooterWheelSetpoint", getSetpoint());
		
		if (controllerStatus) {
			
			speedControllerLeft.pidWrite(-output[0]);
			speedControllerRight.pidWrite(-output[1]);
			
		}
	}
	
	public void spin(double speed) {
		speedControllerLeft.set(-speed);
		speedControllerRight.set(-speed);
	}

	/**
	 * @param input dictates what sensor we are calling the PID loop on -- either "left" or "right"
	 */
	public void bangBangControl() {
		
		double[] input = {returnPIDInputLeft(), returnPIDInputRight()};
		double[] output = new double[2];
		
		if (getSetpoint() > 0) {
			if (input[0] > getSetpoint()) output[0] = 0;
			else output[0] = 1;
			if (input[1] > getSetpoint()) output[1] = 0;
			else output[1] = 1;
		} else if (getSetpoint() < 0) {
			if (input[0] > getSetpoint()) output[0] = 0;
			else output[0] = -1;
			if (input[1] > getSetpoint()) output[1] = 0;
			else output[1] = -1;
		} else output = new double[] {0, 0};
		
		useControllerOutput(output);
		
	}
	
}