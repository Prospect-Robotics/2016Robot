package org.usfirst.frc2813.Robot2016;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.usfirst.frc2813.Robot2016.commands.DisableRecording;
import org.usfirst.frc2813.Robot2016.commands.EnableRecording;
import org.usfirst.frc2813.Robot2016.commands.Intake;
import org.usfirst.frc2813.Robot2016.commands.ResetNav6;
import org.usfirst.frc2813.Robot2016.commands.Shoot;
import org.usfirst.frc2813.Robot2016.commands.TogglePID;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class OI {

	private Joystick joystick1;
	private Joystick joystick2;
	private ArrayList<double[]> inputRecordings;
	private boolean recording;
	private int numberOfFiles = 0;
	public OI() {
		
		inputRecordings = new ArrayList<double[]>();
		joystick1 = new Joystick(0);
		joystick2 = new Joystick(1);

//		new JoystickButton(joystick1, 1).whenPressed(new TogglePID());
//		new JoystickButton(joystick1, 2).whenPressed(new ResetNav6());
//		new JoystickButton(joystick1, 3).whenPressed(new EnableRecording());
//		new JoystickButton(joystick1, 4).whenPressed(new DisableRecording());
		//new JoystickButton(joystick1, 5).whileHeld(new OutputNav6Values());

		new JoystickButton(joystick1, 1).whileHeld(new Shoot());
		new JoystickButton(joystick1, 3).whileHeld(new Intake());

	}

	public Joystick getJoystick1() {
		return joystick1;
	}

	public Joystick getJoystick2() {
		return joystick2;
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