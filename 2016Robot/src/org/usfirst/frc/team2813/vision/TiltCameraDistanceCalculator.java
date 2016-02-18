package org.usfirst.frc.team2813.vision;

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

	int FOVp;    // view port size of the camera in pixels
	double view; // angle of view converted to radians (θ above)
	double hc;	 // camera height in world units (usually feet or meters)
	double h;   // height of the target minus the camera height
	double tilt; // tilt angle converted to radians (α above) 
	
	/**
	 * Create a calculator object to use to resolve the distance of the camera to the target
	 * 
	 * Parameters must be specified in the constructor but you can change them later.
	 * This simplifies the calculation process. If you have a camera that can tilt
	 * during operation you can just update it before executing the distance calculation.
	 * 
	 * @param FOVp view port size of the camera in pixels
	 * @param view angle of view in degrees (θ)
	 * @param hc camera height in world units (usually feet or meters)
	 * @param hT height of the target
	 * @param tilt camera tilt angle in degrees (α) 
	 */
	public TiltCameraDistanceCalculator(int FOVp, double view, double hc, double hT, double tilt) {
		this.FOVp = FOVp;
		this.setView(view);
		this.hc = hc;
		this.h = hT - hc;
		this.setTilt(tilt);
	}
	
	/**
	 * Calculate the distance to the target in world units based on the pixel
	 * offset of a point on the camera's screen.
	 * 
	 * @param hp the distance in pixels from the target to the view axis of the camera
	 * @return the horizontal distance from the target to the camera
	 */
	public double targetDistance(int hp) {
		double fov_adjustment = (FOVp * Math.cos(tilt) / 2 / hp / Math.tan(view)) - Math.sin(tilt);
		return h * fov_adjustment / (1 + fov_adjustment * Math.tan(tilt));
	}

	/**
	 * Update the field of view of the camera. Maybe you have a zoom lens.
	 * 
	 * @param view view angle of view in degrees (θ)
	 */
	public void setView(double view) {
		this.view = view * Math.PI / 180.0;
	}

	/**
	 * Update the tilt angle of the camera
	 * 
	 * @param tilt camera tilt angle in degrees (α) 
	 */
	public void setTilt(double tilt) {
		this.tilt = tilt * Math.PI / 180.0;
	}
	
	/**
	 * Update the camera height. Fancy dollies are a thing
	 * 
	 * @param hc camera height in world units (usually feet or meters)
	 */
	public void setHc(double hc) {
		this.hc = hc;
	}

	/**
	 * Update the target height. Moving targets probably need a more sophisticated algorithm
	 * 
	 * @param hT
	 */
	public void sethT(double hT) {
		this.h = hT - this.geth();
	}

	public int getFOVp() {
		return FOVp;
	}

	public double getHc() {
		return hc;
	}

	public double geth() {
		return h;
	}

	public double getView() {
		return view;
	}

	public double getTilt() {
		return tilt;
	}

}
