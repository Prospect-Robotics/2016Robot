package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {

	private final SpeedController speedControllerLeft = RobotMap.elevatorSpeedControllerLeft;
	private final SpeedController speedControllerRight = RobotMap.elevatorSpeedControllerRight;
	private final Encoder encoder = RobotMap.elevatorEncoder;
	private boolean toggle = false;

	public Elevator() {
		super("Elevator", 1.0, 0.0, 0.0);
		setAbsoluteTolerance(0.2);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("Elevator", "PIDSubsystem Controller",
				getPIDController());
		getPIDController().setOutputRange(-1.0, 1.0);
		enable();
		setSetpoint(0);
	}

	public void initDefaultCommand() {
	}
	
	public void togglePID() {
		toggle = !toggle;
	}

	public double returnPIDInput() {
		return encoder.getDistance();
	}

	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("ElevatorPIDOut", output);
		SmartDashboard.putNumber("ElevatorPIDIn", returnPIDInput());
		if (toggle) {
			move(output);
		}
	}

	public void move(double speed) {
		speedControllerLeft.set(speed);
		speedControllerRight.set(-speed);
	}
}