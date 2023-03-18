package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PneumaticClaw;
import frc.robot.XboxControllers;

public class TeleopClaw extends CommandBase {
    private PneumaticClaw claw;
	private XboxControllers controller;

    public TeleopClaw(PneumaticClaw claw, XboxControllers controller) {
		this.claw = claw;
		this.controller = controller;
		addRequirements(claw);
	}

    @Override
	public void initialize() {}

    @Override
	public void execute() {
		claw.moveClaw(controller.getControllerRBumper(),controller.getControllerLBumper());
	}

    @Override
    public boolean isFinished() {
        return false;
    }
    
    @Override
	public void end(boolean interrupted){} 
}
