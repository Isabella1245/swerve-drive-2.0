package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks{

    private Joystick joystick;

	private double deadbandX = 0;
	private double deadbandY = 0;
    private double deadbandZ = 0;

    public Joysticks(int port) {
		joystick = new Joystick(port); 
	}

    public double getControllerXAxis() {
		if (Math.abs(joystick.getRawAxis(0)) < deadbandX)
			return 0;
		return joystick.getRawAxis(0);
	}

    public double getControllerYAxis() {
		if (Math.abs(joystick.getRawAxis(1)) < deadbandY)
			return 0;
		return joystick.getRawAxis(1) * -1;
	}

    public double getControllerZAxis() {
		if (Math.abs(joystick.getRawAxis(2)) < deadbandZ)
			return 0;
		return joystick.getRawAxis(2);
	}

    //CHANGE SLIDER DEADBAND CODE IF NEEDED
    public double getControllerSlider() {
		if (Math.abs(joystick.getRawAxis(3)) < deadbandZ)
			return 0;
		return joystick.getRawAxis(3);
	}

    public boolean getControllerButton3() {
            return joystick.getRawButton(3);
    }

    public boolean getControllerButton4() {
        return joystick.getRawButton(4);
}

}