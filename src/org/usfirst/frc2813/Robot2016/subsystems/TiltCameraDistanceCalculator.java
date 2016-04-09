package org.usfirst.frc2813.Robot2016.subsystems;

import org.usfirst.frc2813.Robot2016.Robot;

/*
 * Calculates the horizontal distance to a target given camera pixel offset value
 *
 * To find d, the distance of the target from the camera, along the ground
 * See https://drive.google.com/open?id=0B6e2rOpUwmteMWtOREdHSGdqOTg
 *
 * The triangle ΔABC is complementary to ΔDEC
 *
 * FOVy = the height of the camera field of viewX in pixels
 * hT = the height of the the target (in feet or meters)
 * ha = AC, the height of the target above the center line (feet or meters)
 * hp = the image position of the target (in pixels)
 * da = the distance of the target from the camera, along the center line
 *
 * We need to solve the 3 simultaneous equations
 *
 * (1) da = ha × FOVy ÷ (2 hp × tan(θ))
 *
 * (2) cos(α) = DE ÷ DC = d ÷ (da - BC)
 *    → d = cos(α) × (da - BC) = cos(α) × (da - ha × tan(α))
 *        = da cos(α) - ha sin(α)
 *
 * (3) tan(α) = EC ÷ DE = EC ÷ d
 *    also, h = EC + AC = d × tan(α) + ha
 *    → ha = h - d × tan(α)
 *    
 * First combine equations (1) and (2)
 * 	d = ha × FOVy ÷ (2 hp × tan(θ)) × cos(α) - ha sin(α)
 * 
 * Pull out the common factor ha
 *  d = ha [FOVy ÷ (2 hp × tan(θ)) × cos(α) - sin(α)]
 *  
 * Substitute equation (3) for ha
 *  d = (h - d × tan(α)) × [FOVy ÷ (2 hp × tan(θ)) × cos(α) - sin(α)]
 *  
 * Let FOV() = FOVy ÷ (2 hp × tan(θ)) × cos(α) - sin(α)
 *  d = (h - d × tan(α)) × FOV()
 * 
 * Expand
 *  d = h × FOV() - d × tan(α) × FOV()
 * 
 * So h × FOV() = d × [1 + FOV() × tan(α)]
 * 
 * Or d = h × FOV() ÷ [1 + FOV() × tan(α)]
 * 
 */
public class TiltCameraDistanceCalculator {

	private static double tilt = Math.toRadians(30); // Angle of camera from ground
	private static double hT = 226.06; // distance from the center of the goal to the ground
	private static double hc = 32.51;// height of camera from ground (in desired unit)
	private static double wc = 35; // distance from camera to the center of the ball
	private static double FOVx = 640; // width of camera input (in pixels)
	private static double FOVy = 480; // height of camera input (in pixels)
	private static double viewX = Math.toRadians(67.0/2); // Field of viewX of the camera (can find this info online)
	private static double viewY = Math.toRadians(25.5); // Field of viewX of the camera (can find this info online)

	private static double h = hT - hc;
	
	public static double targetAngleOffset() {
		System.out.println("CenterX" + NetworkTables.findGoal()[0]);
		int wp = -(int) Math.round(FOVx/2 - NetworkTables.findGoal()[0]);
		System.out.println("Width distance from center in pixels: " + wp);
		double angle = Math.atan2(2 * wp * Math.tan(viewX), FOVx);
		System.out.println("Angle: " + Math.toDegrees(angle));
		double cameraToShooterAngleOffset = Math.atan2(wc, targetDistance());
		System.out.println("Camera To Shooter Angle Offset: " + Math.toDegrees(cameraToShooterAngleOffset));
		return Math.toDegrees(angle + cameraToShooterAngleOffset); // - for right side robot, + for left side
	}

	/**
	 * Calculate the distance to the target in world units based on the pixel
	 * offset of a point on the camera's screen.
	 * 
	 * @param hp the distance in pixels from the target to the viewX axis of the camera
	 * @return the horizontal distance from the target to the camera
	 */
	public static double targetDistance() {
		System.out.println("CenterY" + NetworkTables.findGoal()[1]);
		int hp = -(int) Math.round(FOVy/2 - NetworkTables.findGoal()[1]);
		System.out.println("Height distance from center in pixels: " + hp);
		if (hp == 0) {
			return h / Math.tan(tilt);
		}
		double fov_adjustment = (FOVy * Math.cos(tilt) / 2 / hp / Math.tan(viewY)) - Math.sin(tilt);
		System.out.println("Camera Distance: " + h * fov_adjustment / (1 + fov_adjustment * Math.tan(tilt)));
		return h * fov_adjustment / (1 + fov_adjustment * Math.tan(tilt));
	}
}
