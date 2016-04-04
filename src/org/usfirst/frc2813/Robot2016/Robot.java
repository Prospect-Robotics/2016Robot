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

import java.util.logging.Logger;

import org.usfirst.frc2813.Robot2016.commands.ObstacleHighGoalAndReset;
import org.usfirst.frc2813.Robot2016.data.DataDisplayer;
import org.usfirst.frc2813.Robot2016.data.DataLogger;
import org.usfirst.frc2813.Robot2016.subsystems.*;
import org.usfirst.frc2813.Robot2016.subsystems.IMU.Nav6;


public class Robot extends IterativeRobot {

	private Command autonomousCommand;

	public static Arms arms;
	public static NetworkTable table;
	public static OI oi;
	public static ShooterAim shooterAim;
	public static ShooterWheels shooterWheels;
	public static Pneumatics pneumatics;
	public static DriveTrain driveTrain;
	public static double bottomGoalY;
	public static double centerX;
	public static double centerY;
	public static double[] goalValues = new double[0];
	public static double[] shootingValues;
	public final static double RANGE_SCALE = 4.88 / 512;
	public static AnalogInput ultrasonicSensor;
	
	public DataDisplayer dataDisplayer;
	public DataLogger dataLogger;
	public static Nav6 nav6;
	
	
	public static AccelerometerSampling accelerometerSampling;
	public static double[] normalPitch;
	
	public static boolean varyShooterSpeeds = false;
	
	private static boolean resetEncoder = true;
	
	public void robotInit() {
		
		autonomousCommand = new ObstacleHighGoalAndReset();
		
		System.out.println("Pre RobotMap Init");
		RobotMap.init();
		System.out.println("Post RobotMap Init");

		System.out.println("Elevator");
		shooterAim = new ShooterAim();
		System.out.println("Shooter Aim");
		shooterWheels = new ShooterWheels();
		System.out.println("Shooter Wheels");
		nav6 = new Nav6();
		System.out.println("Nav6");
		pneumatics = new Pneumatics();
		System.out.println("Pneumatics");
		driveTrain = new DriveTrain();
		System.out.println("DriveTrain");
		arms = new Arms();
		System.out.println("Arms");
		oi = new OI();
		System.out.println("OI");
		ultrasonicSensor = new AnalogInput(3);
		System.out.println("Ultrasonic");
		table = NetworkTable.getTable("GRIP");
		System.out.println("Network");
		accelerometerSampling = new AccelerometerSampling();
		System.out.println("Accelerometer");
		
		System.out.println("Robot Init Done");	

	}

	public void disabledInit() {
		
		Robot.pneumatics.retractShooterPiston();
		
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		
		Robot.pneumatics.retractShooterPiston();
		
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	public void autonomousPeriodic() {
		updateSensorValues();
		if (!RobotMap.limitSwitch.get()) RobotMap.shooterAngleEncoder.reset();

    	displayAndLogData();
    	
		Scheduler.getInstance().run();
	}

	public void teleopInit() {

		Robot.pneumatics.retractShooterPiston();
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
	}

	public void teleopPeriodic() {
		
		resetEncoderCheck();
		
    	updateControllers();
    	
		updateSensorValues();
		lookForGoal();
		
    	displayAndLogData();
    	
		Scheduler.getInstance().run();
		
	}
	
	public void testPeriodic() {
		LiveWindow.run();
	}

	
	public void displayAndLogData() {
		
		dataDisplayer.displayData();
		
	}
	public static void lookForGoal() {
		
		Robot.goalValues = NetworkTables.findGoal();
		if (Robot.goalValues.length == 0) {
			SmartDashboard.putBoolean("GoalFound", false);
		} else if (Robot.goalValues.length >= 2) {
			Robot.centerX = Robot.goalValues[0];
			Robot.centerY = Robot.goalValues[1];
		}
		
	}
	
	public static void updateShootingValues() {
		
    	Robot.shootingValues = TrajectorySimulator.findTrajectory();
		
	}
	
	public static void updateControllers() {
		
		Robot.shooterWheels.bangBangControl();
		Robot.shooterAim.customPID();
		Robot.driveTrain.customPID();
		
	}
	
	public static void updateSensorValues() {

		Robot.accelerometerSampling.update();
		Robot.nav6.updatePitch();
		
		Robot.normalPitch = Robot.nav6.getNormalizedPitch(10);
		
	}
	
	public static void resetEncoderCheck() {
		
		if (RobotMap.limitSwitch.get() && resetEncoder) {
			
			RobotMap.shooterAngleEncoder.reset();
			resetEncoder = false;
			
		} else if (!RobotMap.limitSwitch.get()) resetEncoder = true;
		
	}

}