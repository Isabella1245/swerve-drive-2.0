package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PneumaticClaw;

public class CloseClaw extends CommandBase {
    private PneumaticClaw claw;

    public CloseClaw(PneumaticClaw claw) {
        this.claw = claw;
        addRequirements(claw);
    }

    @Override
    public void execute() {
        claw.closeClaw();
    }

    @Override
    public boolean isFinished() {
        return claw.isClawFinished();
    }
}
