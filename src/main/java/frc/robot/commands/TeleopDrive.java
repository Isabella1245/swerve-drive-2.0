package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveWheelController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.XboxControllers;


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
SmartDashboard.putBoolean("fod", currentFOD);
		if (Robot.LowerDriver.getControllerAButtonPressed()){
			swerve.resetGyro();
		}

		if (Robot.LowerDriver.getControllerBButtonPressed()) {
			currentFOD = !currentFOD;
			swerve.setFOD(currentFOD);
		}
		/*
		 * swerve.drive(Robot.driver.getControllerLeftStickX(), Robot.driver.getControllerLeftStickY(),
					 Robot.driver.getControllerRightStickX(), swerve.gyroAngle());
		 */
		swerve.drive(Robot.LowerDriver.getControllerLeftStickX(), Robot.LowerDriver.getControllerLeftStickY(),
		Robot.LowerDriver.getControllerRightStickX(), swerve.gyroAngle());
	}

	
	@Override
	public void end(boolean interrupted){
		//swerve.stop();
	}
	
}

//add the method teleopdrive interrupted to try to fix turn wheels