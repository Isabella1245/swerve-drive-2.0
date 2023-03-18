package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmWrist;
import frc.robot.XboxControllers;

public class TeleopArmWrist extends CommandBase {
    private ArmWrist armWrist;
	private XboxControllers controller;

    public TeleopArmWrist(ArmWrist armWrist, XboxControllers controller) {
		this.armWrist = armWrist;
		this.controller = controller;
		addRequirements(armWrist);
	}

    @Override
	public void initialize() {}

    @Override
	public void execute() {
		armWrist.armWristControl(controller.getControllerLTrigger(), controller.getControllerRTrigger());
	}

    @Override
    public boolean isFinished() {
        return false;
    }
    
    @Override
	public void end(boolean interrupted){
		armWrist.setWristMotorOutput(0);
	} 
}
