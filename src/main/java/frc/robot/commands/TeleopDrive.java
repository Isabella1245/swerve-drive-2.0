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
		if (Robot.LowerDriver.getControllerButton3()){
			swerve.resetGyro();
		}

		if (Robot.LowerDriver.getControllerButton4()) {
			currentFOD = !currentFOD;
			swerve.setFOD(currentFOD);
		}
		/*
		 * swerve.drive(Robot.driver.getControllerLeftStickX(), Robot.driver.getControllerLeftStickY(),
					 Robot.driver.getControllerRightStickX(), swerve.gyroAngle());
		 */
		swerve.drive(Robot.LowerDriver.getControllerXAxis(), Robot.LowerDriver.getControllerYAxis(),
					 Robot.LowerDriver.getControllerZAxis(), swerve.gyroAngle());
	}

	
	@Override
	public void end(boolean interrupted){
		//swerve.stop();
	}
	
}

//add the method teleopdrive interrupted to try to fix turn wheels