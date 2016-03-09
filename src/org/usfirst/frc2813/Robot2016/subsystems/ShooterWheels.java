package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.shooter.IdleShooterWheels;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterWheels extends Subsystem {

	private boolean pIDStatus; // PID is enabled when true
	private double setpoint; // This is the value we want to get the sensor at -- the goal of the PID loop
	private double marginOfError; // Our target range of speed (in cm/s)

	private boolean shooterSpeedSet; // True when shooter wheels are spinning at the same speed
	
	// These variables are for the custom PID loop to work
	private double newTime;
	private double oldTime;
	private double integral;
	private double previousError;
	
	// Hardware declarations
	private final SpeedController speedControllerLeft;
	private final SpeedController speedControllerRight;
	private final Encoder leftEncoder;
	private final Encoder rightEncoder;

	public ShooterWheels() {

		// PID settings
		pIDStatus = true; // PID is on by default
		setSetpoint(0);
		marginOfError = 0; // A good margin of error is 50cm/s TODO: Change comment to reflect actual good value

		shooterSpeedSet = false; // Assume shooter wheels not spinning at desired speed
		
		// PID variable initiations
		integral = 0;
		previousError = 0;
		oldTime = System.currentTimeMillis();
		
		// Hardware initializations
		speedControllerLeft = RobotMap.shooterSpeedControllerLeft;
		speedControllerRight = RobotMap.shooterSpeedControllerRight;
		leftEncoder = RobotMap.shooterSpeedEncoderLeft;
		rightEncoder = RobotMap.shooterSpeedEncoderRight;
		
	}

	public void initDefaultCommand() {
		setDefaultCommand(new IdleShooterWheels());
	}

	public void enablePID() {
		pIDStatus = true;
	}
	
	public void disablePID() {
		pIDStatus = false;
	}
	
	protected double getSetpoint() {
		return setpoint;
	}
	
	protected void setSetpoint(double setpoint) {
		this.setpoint  = setpoint;
	}

	public double returnPIDInputLeft() {
		return leftEncoder.pidGet();
//		return 0;
	}

	public double returnPIDInputRight() {
		return rightEncoder.pidGet();
//		return 0;
	}

	protected void usePIDOutput(double output, String side) {
		
		if (returnPIDInputLeft() > getSetpoint() - marginOfError &&
		returnPIDInputLeft() < getSetpoint() + marginOfError &&
		returnPIDInputRight()  > getSetpoint() - marginOfError &&
		returnPIDInputRight() < getSetpoint() + marginOfError) {
			integral = 0;
			shooterSpeedSet = true;
		} else shooterSpeedSet = false;
		
		if (pIDStatus) {
			
			if (side.equals("left")) {
				speedControllerLeft.pidWrite(output);
			} else if (side.equals("right")) {
				speedControllerRight.pidWrite(output);
			} else {
				System.out.println("Error: Subsystems/ShooterWheels usePIDOutput, incorrect input for side");
				return;
			}
			
		}
	}
	
	public void spin(double speed) {
		speedControllerLeft.set(-speed);
		speedControllerRight.set(-speed);
	}

	/**
	 * @param input dictates what sensor we are calling the PID loop on -- either "left" or "right"
	 */
	public void customPID(double p, double i, double d, String side) {
		
		double input;
		if (side.equals("left")) {
			input = returnPIDInputLeft();
		} else if (side.equals("right")) {
			input = returnPIDInputRight();
		} else {
			System.out.println("Error: Subsystems/ShooterWheels customPID, incorrect input for side");
			return;
		}
		
		double dt = (newTime - oldTime) / 1000;
		oldTime = newTime;
		double error = getSetpoint() - input;
		integral = integral + error * dt;
		double derivative = (error - previousError) / dt;
		double output = p * error + i * integral + d * derivative;
		previousError = error;
		newTime = System.currentTimeMillis();
		usePIDOutput(output, side);
		
	}
	
}