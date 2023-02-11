package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveWheelController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Controller;


public class TeleopDrive extends CommandBase {

	private SwerveWheelController swerve = null;

	private boolean currentFOD = false;

	public TeleopDrive() {
		swerve = SwerveWheelController.getInstance();

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

	
	@Override
	public void end(boolean interrupted){
		swerve.stop();
	}
	
}

//add the method teleopdrive interrupted to try to fix turn wheels
