package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmWrist;

public class ArmWristMove extends CommandBase {
    private ArmWrist wrist;
    private double setWrist;

    public ArmWristMove(ArmWrist wrist, double setWrist) {
        this.wrist = wrist;
        this.setWrist = setWrist;

        addRequirements(wrist);
    }

    @Override
    public void execute() {
        wrist.setWristPos(setWrist);
    }

    @Override
    public boolean isFinished() {
        return wrist.isWristFinished();
    }
    @Override
    public void end(boolean interrupted){        
        wrist.setWristMotorOutput(0);
    }
}
