package org.usfirst.frc2813.Robot2016;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2813.Robot2016.commands.ImageProcessing;
import org.usfirst.frc2813.Robot2016.subsystems.*;

public class Robot extends IterativeRobot {

	Command autonomousCommand;

	public static AnalogInput ultrasonicSensor;
	public static ImageProcessing imageProcessing;
	public static OI oi;
	public static Elevator elevator;
	public static Nav6 nav6;
	public static IntakeShooter intakeShooter;
	public static Pneumatics pneumatics;
	public static DriveTrain driveTrain;
	public static Arms arms;
	public final double RANGE_SCALE = 4.88 / 512;

	public void robotInit() {
		RobotMap.init();

		elevator = new Elevator();
		intakeShooter = new IntakeShooter();
		nav6 = new Nav6();
		pneumatics = new Pneumatics();
		driveTrain = new DriveTrain();
		arms = new Arms();
		oi = new OI();
		ultrasonicSensor = new AnalogInput(3);
		imageProcessing = new ImageProcessing();

	}

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	public void teleopPeriodic() {
		// nav6.displayNav6Data();
		SmartDashboard.putNumber("Raw", ultrasonicSensor.getValue());
		SmartDashboard.putNumber("Volts", ultrasonicSensor.getVoltage());
		SmartDashboard.putNumber("AvgRaw", ultrasonicSensor.getAverageValue());
		SmartDashboard.putNumber("AvgVolts",
				ultrasonicSensor.getAverageVoltage());
		SmartDashboard.putNumber("Range(In.)", ultrasonicSensor.getVoltage()
				/ RANGE_SCALE);
		SmartDashboard.putNumber("Joy1 X", Robot.oi.getJoystick1().getX());
		SmartDashboard.putNumber("Joy1 Y", Robot.oi.getJoystick1().getY());
		SmartDashboard.putNumber("Joy2 X", Robot.oi.getJoystick2().getX());
		SmartDashboard.putNumber("Joy2 Y", Robot.oi.getJoystick2().getY());
		SmartDashboard.putBoolean("CompressorStatus", Robot.pneumatics.getCompressorStatus());
		Scheduler.getInstance().run();
//		Robot.driveTrain.customPID(DriveTrain.p, DriveTrain.i, DriveTrain.d);
	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}