package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ArmController;


public class TeleopArm extends CommandBase {

    private ArmController arm = null;

    public TeleopArm() {
		arm = ArmController.getInstance();

		addRequirements(arm);
	}

    @Override
	public void initialize() {
		
	}

    @Override
	public void execute() {
		/*
		 * swerve.drive(Robot.driver.getControllerLeftStickX(), Robot.driver.getControllerLeftStickY(),
					 Robot.driver.getControllerRightStickX(), swerve.gyroAngle());
		 */
        //binding the arm to the xbox controller
		arm.armControl(Robot.UpperDriver.getControllerLeftStickY(), Robot.UpperDriver.getControllerRightStickY(),
					 Robot.UpperDriver.getControllerLTrigger(), Robot.UpperDriver.getControllerRTrigger(), 
                     Robot.UpperDriver.getControllerAButtonPressed(), Robot.UpperDriver.getControllerXButtonPressed(), 
					 Robot.UpperDriver.getControllerYButton(), Robot.UpperDriver.getControllerBButton(), Robot.UpperDriver.getControllerRBumper(), Robot.UpperDriver.getControllerLBumper());
	}

    @Override
	public void end(boolean interrupted){
		arm.stop();
	}

}