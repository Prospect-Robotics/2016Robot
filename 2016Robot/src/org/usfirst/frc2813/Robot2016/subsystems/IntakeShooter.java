package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;
import org.usfirst.frc2813.Robot2016.commands.IdleIntakeShooter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class IntakeShooter extends PIDSubsystem {

	private final SpeedController speedControllerLeft = RobotMap.shooterSpeedControllerLeft;
	private final SpeedController speedControllerRight = RobotMap.shooterSpeedControllerRight;
	private final Encoder encoder = RobotMap.shooterEncoder;

	public IntakeShooter() {
		super("Shoot", 1.0, 0.0, 0.0);
		setAbsoluteTolerance(0.2);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("Shoot", "PIDSubsystem Controller",
				getPIDController());
		getPIDController().setOutputRange(-1.0, 1.0);
		disable();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new IdleIntakeShooter());
	}

	protected double returnPIDInput() {
		return encoder.pidGet();
	}

	protected void usePIDOutput(double output) {
		speedControllerLeft.pidWrite(output);
		speedControllerRight.pidWrite(output);
	}

	public void spin(double speed) {
		speedControllerLeft.set(-speed);
		speedControllerRight.set(-speed);
	}
}