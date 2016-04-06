package org.usfirst.frc2813.Robot2016;

//import edu.wpi.first.wpilibj.AnalogGyro;
//import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RobotMap {

//	public static SpeedController elevatorSpeedControllerLeft;
//	public static SpeedController elevatorSpeedControllerRight;
//	public static RobotDrive elevatorSpeedControllers;
//	public static Encoder elevatorEncoder;
	public static SpeedController shooterSpeedControllerLeft;
	public static SpeedController shooterSpeedControllerRight;
	public static SpeedController shooterSpeedControllerAngle;
	public static Encoder shooterAngleEncoder;
	public static Encoder shooterSpeedEncoderLeft;
	public static Encoder shooterSpeedEncoderRight;
	public static SpeedController driveTrainSpeedControllerFrontLeft;
	public static SpeedController driveTrainSpeedControllerFrontRight;
	public static SpeedController driveTrainSpeedControllerBackLeft;
	public static SpeedController driveTrainSpeedControllerBackRight;
	public static RobotDrive driveTrainSpeedControllers;
//	public static AnalogGyro driveTrainGyro;
//	public static AnalogPotentiometer armsPotentiometer;
	public static SpeedController armsSpeedControllerTilt;
	public static SpeedController armsSpeedControllerIntake;
	public static ADXL345_I2C accelerometer;
	public static DigitalInput limitSwitch;
	public static RobotDrive armsSpeedControllers;
	public static Compressor compressor;
	public static Solenoid shooterSolenoidFirstChannel;
	public static Solenoid shooterSolenoidSecondChannel;
	public static Solenoid elevatorSolenoid;

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
		shooterSpeedControllerLeft = new VictorSP(2);
		shooterSpeedControllerRight = new VictorSP(3);
		shooterSpeedControllerAngle = new VictorSP(4);
		
		// LiveWindow.addActuator("ShootFullSpeed", "SpeedController",
		// (VictorSP) shooterSpeedController);
		
		compressor = new Compressor();
		shooterSolenoidFirstChannel = new Solenoid(0);
		shooterSolenoidSecondChannel = new Solenoid(1);
		elevatorSolenoid = new Solenoid(2);

		shooterAngleEncoder = new Encoder(0, 1, false, EncodingType.k4X);
		LiveWindow.addSensor("ShootFullSpeed", "Encoder", shooterAngleEncoder);
		shooterAngleEncoder.setDistancePerPulse(1.0);
		shooterAngleEncoder.setPIDSourceType(PIDSourceType.kRate);
		
		shooterSpeedEncoderLeft = new Encoder(5, 4, false, EncodingType.k4X);
		LiveWindow.addSensor("ShootFullSpeed", "Encoder", shooterSpeedEncoderLeft);
		shooterSpeedEncoderLeft.setDistancePerPulse(1.0);
		shooterSpeedEncoderLeft.setPIDSourceType(PIDSourceType.kRate);
		
		shooterSpeedEncoderRight = new Encoder(3, 2, false, EncodingType.k4X);
		LiveWindow.addSensor("ShootFullSpeed", "Encoder", shooterSpeedEncoderRight);
		shooterSpeedEncoderRight.setDistancePerPulse(1.0);
		shooterSpeedEncoderRight.setPIDSourceType(PIDSourceType.kRate);
		
		accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
		
		limitSwitch = new DigitalInput(9);

		driveTrainSpeedControllerFrontLeft = new VictorSP(9);
		LiveWindow.addActuator("ArcadeDrive", "SpeedControllerFrontLeft",
				(VictorSP) driveTrainSpeedControllerFrontLeft);

		driveTrainSpeedControllerFrontRight = new VictorSP(7);
		LiveWindow.addActuator("ArcadeDrive", "SpeedControllerFrontRight",
				(VictorSP) driveTrainSpeedControllerFrontRight);

		driveTrainSpeedControllerBackLeft = new VictorSP(8);
		LiveWindow.addActuator("ArcadeDrive", "SpeedControllerBackLeft",
				(VictorSP) driveTrainSpeedControllerBackLeft);

		driveTrainSpeedControllerBackRight = new VictorSP(6);
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

		 armsSpeedControllerTilt = new VictorSP(0);
		 LiveWindow.addActuator("Arms", "SpeedControllerTilt",
		 (VictorSP) armsSpeedControllerTilt);
		 
		 armsSpeedControllerIntake = new VictorSP(1);
		 LiveWindow.addActuator("Arms", "SpeedControllerIntake",
		 (VictorSP) armsSpeedControllerIntake);

	}
}