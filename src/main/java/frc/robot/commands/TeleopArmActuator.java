package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmActuator;
import frc.robot.XboxControllers;

public class TeleopArmActuator extends CommandBase {
    private ArmActuator armActuator;
	private XboxControllers controller;

    public TeleopArmActuator(ArmActuator armActuator, XboxControllers controller) {
		this.armActuator = armActuator;
		this.controller = controller;
		addRequirements(armActuator);
	}

    @Override
	public void initialize() {}

    @Override
	public void execute() {
		armActuator.armActuatorControl(controller.getControllerLeftStickY());
	}

    @Override
    public boolean isFinished() {
        return false;
    }
    
    @Override
	public void end(boolean interrupted){
		armActuator.setspeed(0);
	}
}
