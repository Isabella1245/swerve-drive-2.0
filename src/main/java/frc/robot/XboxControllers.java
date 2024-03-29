package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class XboxControllers {

	private XboxController controller;
	

	private double lTriggerDeadband = 0;
	private double rTriggerDeadband = 0;

	private double lJoystickDeadbandX = 0;
	private double lJoystickDeadbandY = 0;

	private double rJoystickDeadbandX = 0;
	private double rJoystickDeadbandY = 0;

	/**
	 * Creates a new Controller to handle input from an Xbox One/360 Controller
	 * @param port
	 */
	public XboxControllers(int port) {
		controller = new XboxController(port); 
	}
	
	/**
	 * Finds whether the Left Joystick is being pushed Left or Right
	 * @return If the Joystick is being pushed Left or Right
	 */
	public double getControllerLeftStickX() {
		if (Math.abs(controller.getRawAxis(0)) < lJoystickDeadbandX)
			return 0;
		return controller.getRawAxis(0);
	}
	/**
	 * Finds whether the Left Joystick is being pushed Up or Down
	 * @return If the Joystick is being pushed Up or Down
	 */
	public double getControllerLeftStickY() {
		if (Math.abs(controller.getRawAxis(1)) < lJoystickDeadbandY)
			return 0;
		return controller.getRawAxis(1);
	}
	/**
	 * Finds whether the Right Joystick is being pushed Left or Right
	 * @return If the Joystick is being pushed Left or Right
	 */
	public double getControllerRightStickX() {
		if (Math.abs(controller.getRawAxis(4)) < rJoystickDeadbandX)
			return 0;
		return controller.getRawAxis(4);
	}
	/**
	 * Finds whether the Right Joystick is being pushed Up or Down
	 * @return If the Joystick is being pushed Up or Down
	 */
	public double getControllerRightStickY() {
		if (Math.abs(controller.getRawAxis(5)) < rJoystickDeadbandY)
			return 0;
		return controller.getRawAxis(5);
	}
	/**
	 * Finds how much the Left trigger is being pushed (analog input)
	 * @return How much is the trigger being pushed down
	 */
	public double getControllerLTrigger() {
		if (Math.abs(controller.getRawAxis(2)) < lTriggerDeadband)
			return 0;
		return controller.getRawAxis(2);
	}
	/**
	 * Finds how much the Right trigger is being pushed (analog input)
	 * @return How much is the trigger being pushed down
	 */
	public double getControllerRTrigger() {
		if (Math.abs(controller.getRawAxis(3)) < rTriggerDeadband)
			return 0;
		return controller.getRawAxis(3);
	}
	/**
	 * Checks if the Left Bumper was pressed since the last check
	 * @return Whether the button was pressed since the last check
	 */
	public boolean getControllerLBumperPressed() {
		return controller.getRawButtonPressed(5);
	}
	/**
	 * Checks the state of the Left Bumper
	 * @return The State of the button
	 */
	public boolean getControllerLBumper() {
		return controller.getLeftBumper();
	}
	//Trigger lBumper = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
	public Trigger lBumper() {
		return new Trigger(() -> controller.getLeftBumper());
	}

	/**
	 * Checks if the Right Bumper was pressed since the last check
	 * @return Whether the button was pressed since the last check
	 */
	public boolean getControllerRBumperPressed() {
		return controller.getRawButtonPressed(6);
	}
	/**
	 * Checks the state of the Right Bumper
	 * @return The State of the button
	 */
	public boolean getControllerRBumper() {
		return controller.getRightBumper();
	}
	//Trigger rBumper = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
	public Trigger rBumper() {
		return new Trigger(() -> controller.getRightBumper());
	} 

	/**
	 * Checks if the A button was pressed since the last check
	 * @return Whether the button was pressed since the last check
	 */
	public boolean getControllerAButtonPressed() {
		return controller.getAButtonPressed();
	}
	/**
	 * Checks the state of the A button
	 * @return The State of the button
	 */
	public boolean getControllerAButton() {
		return controller.getAButton();
	}
	//Trigger aButton = new JoystickButton(controller, XboxController.Button.kA.value);
	public Trigger aButton() {
		return new Trigger(() -> controller.getAButton());
	}
	/**
	 * Checks if the B button was pressed since the last check 
	 * @return Whether the button was pressed since the last check
	 */
	public boolean getControllerBButtonPressed() {
		return controller.getBButtonPressed();
	}
	//Trigger bButton = new JoystickButton(controller, XboxController.Button.kB.value);
	public Trigger bButton() {
		return new Trigger(() -> controller.getBButton());
	}

	/**
	 * Checks the state of the B button
	 * @return The State of the button
	 */
	public boolean getControllerBButton() {
		return controller.getBButton();
	}
	/**
	 * Checks if the X button was pressed since the last check 
	 * @return Whether the button was pressed since the last check 
	 */
	public boolean getControllerXButtonPressed() {
		return controller.getXButtonPressed();
	}
	//Trigger xButton = new JoystickButton(controller, XboxController.Button.kX.value);
	public Trigger xButton() {
		return new Trigger(() -> controller.getXButton());
	}

	/**
	 * Checks if the Y button was pressed since the last check 
	 * @return Whether the button was pressed since the last check
	 */
	public boolean getControllerYButtonPressed() {
		return controller.getYButtonPressed();
	}

	public boolean getControllerYButton() {
		return controller.getYButton();
	}
	//Trigger yButton = new JoystickButton(controller, XboxController.Button.kY.value);
	public Trigger yButton() {
		return new Trigger(() -> controller.getYButton());
	}

	/**
	 * Checks if the Start button was pressed since the last check
	 * @return Whether the button was pressed since last check 
	 */
	public boolean getControllerStartButtonPressed() {
		return controller.getStartButtonPressed();
	}
	/**
	 * Checks the state of the Start button
	 * @return The State of the button
	 */
	public boolean getControllerStartButton() {
		return controller.getStartButton();
	}

	/**
	 * Sets the value of the deadband on the Left Stick X axis 
	 * @param value
	 * @return Deadband of the joystick
	 */
	public void setControllerLeftStickXDeadband(double value) {
		lJoystickDeadbandX = value;
	}
	/**
	 * Sets the value of the deadband on the Left Stick Y axis 
	 * @param value
	 * @return Deadband of the joystick
	 */
	public void setControllerLeftStickYDeadband(double value) {
		lJoystickDeadbandY = value;
	}
	/**
	 * Sets the value of the deadband on the Right Stick X axis 
	 * @param value
	 * @return Deadband of the joystick
	 */
	public void setControllerRightStickXDeadband(double value) {
		rJoystickDeadbandX = value;
	}
	/**
	 * Sets the value of the deadband on the Right Stick Y axis 
	 * @param value
	 * @return Deadband of the joystick
	 */
	public void setControllerRightStickYDeadband(double value) {
		rJoystickDeadbandY = value;
	}
	/**
	 * Sets the value of the deadband on the Left Trigger 
	 * @param value
	 * @return Deadband of the trigger
	 */
	public void setControllerLeftTriggerDeadband(double value) {
		lTriggerDeadband = value;
	}
	/**
	 * Sets the value of the deadband on the Right Trigger 
	 * @param value
	 * @return Deadband of the trigger
	 */
	public void setControllerRightTriggerDeadband(double value) {
		rTriggerDeadband = value;
	}
}