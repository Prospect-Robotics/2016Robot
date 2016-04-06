package org.usfirst.frc2813.Robot2016;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.usfirst.frc2813.Robot2016.commands.arms.ArmsJogUp;
import org.usfirst.frc2813.Robot2016.commands.driving.RotateRobotToGoal;
import org.usfirst.frc2813.Robot2016.commands.driving.TurnToOpponentAlliance;
import org.usfirst.frc2813.Robot2016.commands.driving.TurnToOurAlliance;
import org.usfirst.frc2813.Robot2016.commands.arms.ArmsIntake;
import org.usfirst.frc2813.Robot2016.commands.arms.ArmsJogDown;
import org.usfirst.frc2813.Robot2016.commands.shooter.ToggleShooterPiston;
import org.usfirst.frc2813.Robot2016.commands.shooter.autoShooting.SetShooterAngleToGoal;
import org.usfirst.frc2813.Robot2016.commands.shooter.autoShooting.ShootIntoGoal;
import org.usfirst.frc2813.Robot2016.commands.shooter.jogging.ShooterManualJogDown;
import org.usfirst.frc2813.Robot2016.commands.shooter.jogging.ShooterManualJogUp;
import org.usfirst.frc2813.Robot2016.commands.shooter.jogging.ShooterSensorJogDown;
import org.usfirst.frc2813.Robot2016.commands.shooter.jogging.ShooterSensorJogUp;
import org.usfirst.frc2813.Robot2016.commands.shooter.testing.DecreaseShooterTestSpeed;
import org.usfirst.frc2813.Robot2016.commands.shooter.testing.IncreaseShooterTestSpeed;
import org.usfirst.frc2813.Robot2016.commands.shooter.testing.SetShooterToTestSpeed;
import org.usfirst.frc2813.Robot2016.commands.shooter.ShootFullSpeed;
import org.usfirst.frc2813.Robot2016.commands.shooter.ShooterIntake;
import org.usfirst.frc2813.Robot2016.commands.shooter.ShooterOuttake;
import org.usfirst.frc2813.Robot2016.commands.shooter.ShooterToBottom;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;


public class OI {

	private Joystick joystick1;
	private Joystick joystick2;
	private Joystick operator;
	private ArrayList<double[]> inputRecordings;
	private boolean recording;
	private int numberOfFiles = 0;
	public OI() {
		
		inputRecordings = new ArrayList<double[]>();
		joystick1 = new Joystick(0);
		joystick2 = new Joystick(1);
		operator = new Joystick(2);
		
		new JoystickButton(joystick1, 1).whenPressed(new ShootIntoGoal());
		new JoystickButton(joystick1, 2).whenPressed(new SetShooterToTestSpeed());
		new JoystickButton(joystick1, 4).whileHeld(new TurnToOurAlliance());
		new JoystickButton(joystick1, 5).whileHeld(new TurnToOpponentAlliance());
		new JoystickButton(joystick1, 8).whenPressed(new IncreaseShooterTestSpeed());
		new JoystickButton(joystick1, 9).whenPressed(new DecreaseShooterTestSpeed());
		
		new JoystickButton(operator, 1).whenPressed(new ShooterToBottom());
		new JoystickButton(operator, 2).whileHeld(new ShooterOuttake());
		new JoystickButton(operator, 3).whenPressed(new ToggleShooterPiston());
		new JoystickButton(operator, 5).whileHeld(new ShooterManualJogUp());
		new JoystickButton(operator, 6).whileHeld(new ArmsJogUp());
		new JoystickButton(operator, 7).whileHeld(new ShooterIntake());
		new JoystickButton(operator, 8).whileHeld(new SetShooterAngleToGoal());
		new JoystickButton(operator, 9).whileHeld(new ShooterManualJogDown());
		new JoystickButton(operator, 10).whileHeld(new ArmsJogDown());
		new JoystickButton(operator, 11).whileHeld(new ArmsIntake());
		new JoystickButton(operator, 12).whenPressed(new ShootIntoGoal());
		
	}

	public Joystick getJoystick1() {
		return joystick1;
	}

	public Joystick getJoystick2() {
		return joystick2;
	}
	
	public Joystick getOperator() {
		return operator;
	}
	
	public void addInputs(double[] inputs) {
		if (recording) inputRecordings.add(inputs);
	}
	
	public void writeInputsToFile() {
		
		while (inputRecordings.size() > 50000) {
			inputRecordings.remove(0);
		}
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("/home/lvuser/RecordedInputs" + numberOfFiles + ".txt"), "utf-8"))) {
			numberOfFiles++;
			writer.write("[");
			for (double[] inputs : inputRecordings) 
				writer.write(inputs[0] + ", " + inputs[1] + ", " + inputs[2] + ", " + inputs[3] + " ||| ");
			writer.write("]");
		} catch (Exception ex) {
			
		}
		
	}
	
	public void enableRecording() {
		recording = true;
	}
	
	public void disableRecording() {
		recording = false;
		writeInputsToFile();
		inputRecordings.clear();
	}

}