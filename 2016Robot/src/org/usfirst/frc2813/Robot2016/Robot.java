package org.usfirst.frc2813.Robot2016;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2813.Robot2016.subsystems.*;

public class Robot extends IterativeRobot {

	Command autonomousCommand;

	public static OI oi;
	public static Elevator elevator;
	public static Nav6 nav6;
	public static Shooter shooter;
	public static Intake intake;
	public static Pneumatics pneumatics;
	public static DriveTrain driveTrain;
	public static Arms arms;
	public static AnalogInput ultrasonicSensor;
	public final double RANGE_SCALE = 4.88/512;
	
	public void robotInit() {
		RobotMap.init();

		elevator = new Elevator();
		shooter = new Shooter();
		nav6 = new Nav6();
		intake = new Intake();
		pneumatics = new Pneumatics();
		driveTrain = new DriveTrain();
		arms = new Arms();
		oi = new OI();
		ultrasonicSensor = new AnalogInput(3);
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
//		nav6.displayNav6Data();
		SmartDashboard.putNumber("Raw", ultrasonicSensor.getValue());
		SmartDashboard.putNumber("Volts", ultrasonicSensor.getVoltage());
		SmartDashboard.putNumber("AvgRaw", ultrasonicSensor.getAverageValue());
		SmartDashboard.putNumber("AvgVolts", ultrasonicSensor.getAverageVoltage());
		SmartDashboard.putNumber("Range(In.):", ultrasonicSensor.getVoltage()/RANGE_SCALE);
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}