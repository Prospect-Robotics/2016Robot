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
import org.usfirst.frc2813.Robot2016.commands.arms.SafeArmsUp;
import org.usfirst.frc2813.Robot2016.data.DataDisplayer;
import org.usfirst.frc2813.Robot2016.data.DataLogger;
import org.usfirst.frc2813.Robot2016.subsystems.*;
import org.usfirst.frc2813.Robot2016.subsystems.IMU.Nav6;


public class Robot extends IterativeRobot {

	private Command autonomousCommand;

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
		
		System.out.println("Pre RobotMap Init");
		RobotMap.init();
		System.out.println("Post RobotMap Init");

		System.out.println("Elevator");
		shooterAim = new ShooterAim();
		System.out.println("Shooter Aim");
		shooterWheels = new ShooterWheels();
		System.out.println("Shooter Wheels");
		nav6 = new Nav6();
		if(nav6 != null && nav6.isValid()) {
			System.out.println("Nav6");
		} else {
			nav6 = null;
			System.out.println("Nav6 is offline.  PID disabled.");
		}
		pneumatics = new Pneumatics();
		System.out.println("Pneumatics");
		driveTrain = new DriveTrain();
		System.out.println("DriveTrain");
		oi = new OI();
		System.out.println("OI");
		ultrasonicSensor = new AnalogInput(3);
		System.out.println("Ultrasonic");
		table = NetworkTable.getTable("GRIP");
		System.out.println("Network");
		accelerometerSampling = new AccelerometerSampling();
		System.out.println("Accelerometer");
		dataDisplayer = new DataDisplayer();
		System.out.println("DataDisplayer");
		
		System.out.println("Robot init done");
		
		autonomousCommand = new ObstacleHighGoalAndReset();
		System.out.println("Autonomous command initialized");
		
		(new SafeArmsUp()).start();
		System.out.println("Arms retracted");

	}

	public void disabledInit() {
		
		Robot.pneumatics.retractShooterPiston();
		Robot.pneumatics.retractArms();
		
	}

	public void disabledPeriodic() {
		updateSensorValues();
		displayAndLogData();
		Scheduler.getInstance().run();
		
	}

	public void autonomousInit() {
		
		Robot.pneumatics.retractShooterPiston();
		
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	public void autonomousPeriodic() {
		updateSensorValues();

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
			SmartDashboard.putBoolean("GoalFound", true);
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
		if (Robot.nav6 != null) {
			Robot.nav6.updatePitch();
			Robot.nav6.updateYaw();
			
			Robot.normalPitch = Robot.nav6.getNormalizedPitch(10);
		}
			
		if (!RobotMap.limitSwitch.get()) RobotMap.shooterAngleEncoder.reset();
		
	}
	
	public static void resetEncoderCheck() {
		
		if (RobotMap.limitSwitch.get() && resetEncoder) {
			
			RobotMap.shooterAngleEncoder.reset();
			resetEncoder = false;
			
		} else if (!RobotMap.limitSwitch.get()) resetEncoder = true;
		
	}

}