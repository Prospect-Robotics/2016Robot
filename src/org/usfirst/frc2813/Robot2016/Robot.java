package org.usfirst.frc2813.Robot2016;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2813.Robot2016.commands.ImageProcessing;
import org.usfirst.frc2813.Robot2016.subsystems.*;


public class Robot extends IterativeRobot {

	Command autonomousCommand;

	public static AnalogInput ultrasonicSensor;
	public static NetworkTable table;
	public static OI oi;
	public static Elevator elevator;
	public static Nav6 nav6;
	public static ShooterAim shooterAim;
	public static ShooterWheels shooterWheels;
	public static Pneumatics pneumatics;
	public static DriveTrain driveTrain;
	public static Arms arms;
	public static double bottomGoalY;
	public static double[] goalValues;
	public static double[] autoShooterValues;
	public final double RANGE_SCALE = 4.88 / 512;
	public static AccelerometerSampling avgAccel;
	
	public void robotInit() {
		System.out.println("TEST1");
		RobotMap.init();

		elevator = new Elevator();
		shooterAim = new ShooterAim();
		shooterWheels = new ShooterWheels();
		nav6 = new Nav6();
		pneumatics = new Pneumatics();
		driveTrain = new DriveTrain();
		arms = new Arms();
		oi = new OI();
		ultrasonicSensor = new AnalogInput(3);
		table = NetworkTable.getTable("GRIP");
		avgAccel = new AccelerometerSampling();
		

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
		if (!RobotMap.limitSwitch.get()) RobotMap.shooterEncoder.reset();
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
//    	Robot.shooterAim.disablePID();
//    	Robot.shooterAim.setSpeed(-0.15);
//    	while (RobotMap.limitSwitch.get()) {
//    		Timer.delay(0.05);
//    	}
//		Robot.shooterAim.setSpeed(0.05);
//		while (!RobotMap.limitSwitch.get()) {
//			Timer.delay(0.05);
//		}
//		Robot.shooterAim.setAngle(0);
//		Robot.shooterAim.enablePID();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	public void teleopPeriodic() {
//		avgAccel.update();
		// nav6.displayNav6Data();
//		SmartDashboard.putNumber("Raw", ultrasonicSensor.getValue());
//		SmartDashboard.putNumber("Volts", ultrasonicSensor.getVoltage());
//		SmartDashboard.putNumber("AvgRaw", ultrasonicSensor.getAverageValue());
//		SmartDashboard.putNumber("AvgVolts",
//				ultrasonicSensor.getAverageVoltage());
//		SmartDashboard.putNumber("Range(In.)", ultrasonicSensor.getVoltage()
//				/ RANGE_SCALE);
//		SmartDashboard.putNumber("Joy1 X", Robot.oi.getJoystick1().getX());
//		SmartDashboard.putNumber("Joy1 Y", Robot.oi.getJoystick1().getY());
//		SmartDashboard.putNumber("Joy2 X", Robot.oi.getJoystick2().getX());
//		SmartDashboard.putNumber("Joy2 Y", Robot.oi.getJoystick2().getY());
		SmartDashboard.putNumber("AccelX", RobotMap.accelerometer.getX());
		SmartDashboard.putNumber("AccelY", RobotMap.accelerometer.getY());
		SmartDashboard.putNumber("AccelZ", RobotMap.accelerometer.getZ());
		
		SmartDashboard.putBoolean("CompressorStatus", Robot.pneumatics.getCompressorStatus());
		avgAccel.update();
//		System.out.println("AccelerometerSampling Angle: " + (88.3 -(Math.atan2(avgAccel.getYAvg(), avgAccel.getXAvg()) * 180.0) / Math.PI));
//		System.out.println("ENCODER VALUE: " + RobotMap.shooterEncoder.getDistance());
//		System.out.println("MAGNET SENSOR: " + RobotMap.limitSwitch.get());
		if (!RobotMap.limitSwitch.get()) RobotMap.shooterEncoder.reset();
		goalValues = ImageProcessing.findGoal();
		if (goalValues.length == 0) {
			SmartDashboard.putBoolean("GoalFound", false);
		} else if (goalValues.length >= 8) {
			Robot.bottomGoalY = (goalValues[5] + goalValues[7]) / 2;
			SmartDashboard.putBoolean("GoalFound", true);
		}
		Robot.shooterAim.customPID(0.018, 0.0000, 0.0);
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}