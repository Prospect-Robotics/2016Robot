package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.RobotMap;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

//take samples to get average. I don't know stats
public class AvgAccelerometer extends Subsystem {

	double accelX;
	double accelY;
	double accelZ;
	double sumX = 0;
	double sumY = 0;
	double sumZ = 0;
	
	int samples = 0;
	int requiredSamples = 100;
	ADXL345_I2C accelerometer = RobotMap.accelerometer;
	
	@Override
	protected void initDefaultCommand() {

		accelX = accelerometer.getX();
		accelY = accelerometer.getY();
		accelZ = accelerometer.getZ();
		
	}
	
	public void update(){
		if(samples >= requiredSamples){
			accelX = sumX/samples;
			accelY = sumY/samples;
			accelZ = sumZ/samples;
			samples = 0;
			sumX = 0;
			sumY = 0;
			sumZ = 0;
		}
		else{
			samples++;
			sumX+=accelerometer.getX();
			sumY+=accelerometer.getY();
			sumZ+=accelerometer.getZ();			
		}
	}

	public double getX(){
		return accelX;
	}
	public double getY(){
		return accelY;
	}
	public double getZ(){
		return accelZ;
	}
    
	
}

