package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmController;

public class SetExtensionPos extends CommandBase{
    private ArmController arm;
    private double setExtension;

    public SetExtensionPos(ArmController arm, double setExtension) {
        this.arm = arm;
        this.setExtension = setExtension;

        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.setExtensionPos(setExtension);
    }
}
