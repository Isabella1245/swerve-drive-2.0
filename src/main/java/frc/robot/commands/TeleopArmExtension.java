package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtension;
import frc.robot.XboxControllers;

public class TeleopArmExtension extends CommandBase {
    private ArmExtension armExtension;
	private XboxControllers controller;

    public TeleopArmExtension(ArmExtension armExtension, XboxControllers controller) {
		this.armExtension = armExtension;
		this.controller = controller;
		addRequirements(armExtension);
	}

    @Override
	public void initialize() {}

    @Override
	public void execute() {
		armExtension.armExtensionControl(controller.getControllerRightStickY());
	}

    @Override
    public boolean isFinished() {
        return false;
    }
    
    @Override
	public void end(boolean interrupted){
		armExtension.setExtensionMotorOutput(0);
	}    
}
