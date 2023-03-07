package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveWheel;
import frc.robot.subsystems.SwerveWheelController;
import frc.robot.subsystems.SwerveWheelDrive.SwerveWheelDriveType;
import frc.robot.subsystems.ArmPart;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;



public class Auton1 extends CommandBase{
    
    private SwerveWheelController swerve = null;
    private Timer timer = new Timer();

    public Auton1() {
		swerve = SwerveWheelController.getInstance();

		addRequirements(swerve);
	}


    @Override
	public void initialize() {
        timer.reset();
        timer.start();
		
	}

    @Override
	public void execute() {
		/*
		 * swerve.drive(Robot.driver.getControllerLeftStickX(), Robot.driver.getControllerLeftStickY(),
					 Robot.driver.getControllerRightStickX(), swerve.gyroAngle());
		 */
        //binding the arm to the xbox controller
		swerve.auton(timer.get());
	}

    @Override
	public void end(boolean interrupted){
		swerve.stop();
        timer.stop();
	}
}
