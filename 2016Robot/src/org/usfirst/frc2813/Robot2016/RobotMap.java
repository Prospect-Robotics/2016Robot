package org.usfirst.frc2813.Robot2016;

//import edu.wpi.first.wpilibj.AnalogGyro;
//import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RobotMap {

//	public static SpeedController elevatorSpeedControllerLeft;
//	public static SpeedController elevatorSpeedControllerRight;
//	public static RobotDrive elevatorSpeedControllers;
//	public static Encoder elevatorEncoder;
	public static SpeedController shooterSpeedControllerLeft;
	public static SpeedController shooterSpeedControllerRight;
	public static SpeedController shooterSpeedControllerAngle;
	public static Encoder shooterEncoder;
	public static SpeedController driveTrainSpeedControllerFrontLeft;
	public static SpeedController driveTrainSpeedControllerFrontRight;
	public static SpeedController driveTrainSpeedControllerBackLeft;
	public static SpeedController driveTrainSpeedControllerBackRight;
	public static RobotDrive driveTrainSpeedControllers;
//	public static AnalogGyro driveTrainGyro;
//	public static AnalogPotentiometer armsPotentiometer;
	public static SpeedController armsSpeedControllerLeft;
	public static SpeedController armsSpeedControllerRight;
	public static SpeedController armsSpeedControllerSucker;
	public static RobotDrive armsSpeedControllers;
	public static Compressor compressor;
	public static Solenoid shooterSolenoid;
	public static Solenoid elevatorSolenoid;
	public static Solenoid solenoid3;
	public static Solenoid solenoid4;
	public static Solenoid solenoid5;

	public static void init() {
		// elevatorSpeedControllerLeft = new VictorSP(8);
		// LiveWindow.addActuator("Elevator", "SpeedControllerLeft",
		// (VictorSP) elevatorSpeedControllerLeft);

		// elevatorSpeedControllerRight = new VictorSP(9);
		// LiveWindow.addActuator("Elevator", "SpeedControllerRight",
		// (VictorSP) elevatorSpeedControllerRight);

		// elevatorSpeedControllers = new
		// RobotDrive(elevatorSpeedControllerLeft,
		// elevatorSpeedControllerRight);
		//
		// elevatorSpeedControllers.setSafetyEnabled(true);
		// elevatorSpeedControllers.setExpiration(0.1);
		// elevatorSpeedControllers.setSensitivity(0.5);
		// elevatorSpeedControllers.setMaxOutput(1.0);

		// elevatorEncoder = new Encoder(0, 1, false, EncodingType.k4X);
		// LiveWindow.addSensor("Elevator", "Encoder", elevatorEncoder);
		// elevatorEncoder.setDistancePerPulse(1.0);
		// elevatorEncoder.setPIDSourceType(PIDSourceType.kRate);
		shooterSpeedControllerLeft = new VictorSP(7);
		shooterSpeedControllerRight = new VictorSP(5);
		shooterSpeedControllerAngle = new VictorSP(4);
		// LiveWindow.addActuator("Shoot", "SpeedController",
		// (VictorSP) shooterSpeedController);
		
		compressor = new Compressor();
		shooterSolenoid = new Solenoid(0);
		elevatorSolenoid = new Solenoid(1);
		solenoid3 = new Solenoid(2);
		solenoid4 = new Solenoid(3);
		solenoid5 = new Solenoid(4);

		shooterEncoder = new Encoder(2, 3, false, EncodingType.k4X);
		LiveWindow.addSensor("Shoot", "Encoder", shooterEncoder);
		shooterEncoder.setDistancePerPulse(1.0);
		shooterEncoder.setPIDSourceType(PIDSourceType.kRate);

		driveTrainSpeedControllerFrontLeft = new VictorSP(2);
		LiveWindow.addActuator("ArcadeDrive", "SpeedControllerFrontLeft",
				(VictorSP) driveTrainSpeedControllerFrontLeft);

		driveTrainSpeedControllerFrontRight = new VictorSP(1);
		LiveWindow.addActuator("ArcadeDrive", "SpeedControllerFrontRight",
				(VictorSP) driveTrainSpeedControllerFrontRight);

		driveTrainSpeedControllerBackLeft = new VictorSP(3);
		LiveWindow.addActuator("ArcadeDrive", "SpeedControllerBackLeft",
				(VictorSP) driveTrainSpeedControllerBackLeft);

		driveTrainSpeedControllerBackRight = new VictorSP(0);
		LiveWindow.addActuator("ArcadeDrive", "SpeedControllerBackRight",
				(VictorSP) driveTrainSpeedControllerBackRight);

		driveTrainSpeedControllers = new RobotDrive(
				driveTrainSpeedControllerFrontLeft,
				driveTrainSpeedControllerBackLeft,
				driveTrainSpeedControllerFrontRight,
				driveTrainSpeedControllerBackRight);
//
//		driveTrainSpeedControllers.setSafetyEnabled(true);
//		driveTrainSpeedControllers.setExpiration(0.1);
//		driveTrainSpeedControllers.setSensitivity(0.5);
//		driveTrainSpeedControllers.setMaxOutput(1.0);

//		driveTrainGyro = new AnalogGyro(0);
//		LiveWindow.addSensor("ArcadeDrive", "Gyro", driveTrainGyro);
//		driveTrainGyro.setSensitivity(0.007);
//		armsPotentiometer = new AnalogPotentiometer(1, 1.0, 0.0);
//		LiveWindow.addSensor("Arms", "Potentiometer", armsPotentiometer);

		// armsSpeedControllerLeft = new VictorSP(9);
		// LiveWindow.addActuator("Arms", "SpeedControllerLeft",
		// (VictorSP) armsSpeedControllerLeft);

		armsSpeedControllerRight = new VictorSP(10);
		LiveWindow.addActuator("Arms", "SpeedControllerRight",
				(VictorSP) armsSpeedControllerRight);

		// armsSpeedControllers = new RobotDrive(armsSpeedControllerLeft,
		// armsSpeedControllerRight);

		// armsSpeedControllers.setSafetyEnabled(true);
		// armsSpeedControllers.setExpiration(0.1);
		// armsSpeedControllers.setSensitivity(0.5);
		// armsSpeedControllers.setMaxOutput(1.0);
	}
}