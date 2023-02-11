package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Class dealing with the configuration of the swerve wheel drive system
 */
public class SwerveWheelDrive {

	public enum SwerveWheelDriveType { TalonSRX }

	SwerveWheelDriveType type;

	MotorController controller;

	//double driveENC;

	/**
	 * Configures specified motor type for the swerve wheel drive system. 
	 * @param type Motor type
	 * @param id CAN ID
	 * @param inverted Inverts the motor 
	 */
	public SwerveWheelDrive(SwerveWheelDriveType type, int id, boolean inverted) {
		if (type == SwerveWheelDriveType.TalonSRX) {

			// Create TalonSRX object with the ID from the constructor
			WPI_TalonSRX drive = new WPI_TalonSRX(id);

			drive.configFactoryDefault();

			// Invert the motor depending on the inverted value
			drive.setInverted(inverted);

			// WPI_TalonSRX can be passed into many different WPILib objects like the
			// SpeedController
			controller = drive;
		}
	}
	
	/**
	 * Sets speed of the swerve wheel drive motor controller
	 * @param speed
	 */
	public void setSpeed(double speed) {
		controller.set(speed);
	}
	
	
}