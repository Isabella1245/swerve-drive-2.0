package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmController;

public class SetArmRotationPos extends CommandBase{
    private ArmController arm;
    private double setWrist;

    public SetArmRotationPos(ArmController arm, double setWrist) {
        this.arm = arm;
        this.setWrist = setWrist;

        addRequirements(arm);
    }

    @Override 
    public void execute() {
        arm.setArmRotationPos(setWrist);
    }
}
