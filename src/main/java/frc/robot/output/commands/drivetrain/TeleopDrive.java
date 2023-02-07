package frc.robot.output.commands.drivetrain;

import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.SwerveWheelController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;

/*
public class TeleopDrive extends CommandBase {

	private Joystick swerve = null;

	private boolean currentFOD = false;

	public TeleopDrive() {
		swerve = Joystick.getInstance();

		addRequirements(swerve);
	}

	@Override
	public void initialize() {
		currentFOD = swerve.getFOD();

		swerve.resetGyro();
	}

	@Override
	public void execute() {
		if (Robot.driver.getControllerAButtonPressed()) {
			swerve.resetGyro();
		}

		if (Robot.driver.getControllerBButtonPressed()) {
			currentFOD = !currentFOD;
			swerve.setFOD(currentFOD);
		}

		swerve.drive(Robot.driver.getControllerLeftStickX(), Robot.driver.getControllerLeftStickY(),
					 Robot.driver.getControllerRightStickX(), swerve.gyroAngle());
	}
}
*/