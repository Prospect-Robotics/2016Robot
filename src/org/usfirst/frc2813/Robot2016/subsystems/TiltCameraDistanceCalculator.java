package org.usfirst.frc2813.Robot2016.subsystems;

/*
 * Calculates the horizontal distance to a target given camera pixel offset value
 *
 * To find d, the distance of the target from the camera, along the ground
 * See https://drive.google.com/open?id=0B6e2rOpUwmteMWtOREdHSGdqOTg
 *
 * The triangle ΔABC is complementary to ΔDEC
 *
 * FOVp = the height of the camera field of view in pixels
 * hT = the height of the the target (in feet or meters)
 * ha = AC, the height of the target above the center line (feet or meters)
 * hp = the image position of the target (in pixels)
 * da = the distance of the target from the camera, along the center line
 *
 * We need to solve the 3 simultaneous equations
 *
 * (1) da = ha × FOVp ÷ (2 hp × tan(θ))
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
 * 	d = ha × FOVp ÷ (2 hp × tan(θ)) × cos(α) - ha sin(α)
 * 
 * Pull out the common factor ha
 *  d = ha [FOVp ÷ (2 hp × tan(θ)) × cos(α) - sin(α)]
 *  
 * Substitute equation (3) for ha
 *  d = (h - d × tan(α)) × [FOVp ÷ (2 hp × tan(θ)) × cos(α) - sin(α)]
 *  
 * Let FOV() = FOVp ÷ (2 hp × tan(θ)) × cos(α) - sin(α)
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
	
	/**
	 * Calculate the distance to the target in world units based on the pixel
	 * offset of a point on the camera's screen.
	 * 
	 * @param hp the distance in pixels from the target to the view axis of the camera
	 * @return the horizontal distance from the target to the camera
	 */
	public static double targetDistance(int hp) {
		double tilt = Math.toRadians(27);
		double hT = 210.82;
		double hc = 29.21;
		double FOVp = 480;
		double view = Math.toRadians(24.5);
		double h = hT - hc;
		if (hp == 0) {
			return h / Math.tan(tilt);
		}
		double fov_adjustment = (FOVp * Math.cos(tilt) / 2 / hp / Math.tan(view)) - Math.sin(tilt);
		return h * fov_adjustment / (1 + fov_adjustment * Math.tan(tilt));
	}

}
