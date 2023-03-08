package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmController;


public class ArmMidHeight extends CommandBase{
    private ArmController arm;

    public ArmMidHeight(ArmController arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void execute(){
        arm.midHeightArm(true);
    }
}
