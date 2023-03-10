package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmController;

public class SetActuatorArmPos extends CommandBase{
    private ArmController arm;
    private double setHeight;

    public SetActuatorArmPos(ArmController arm, double setHeight) {
        this.arm = arm;
        this.setHeight = setHeight;

        addRequirements(arm);
    }

    @Override 
    public void execute() {
        arm.setActuatorArmPos(setHeight);
    }
    
}
