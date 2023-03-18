package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtension;

public class ArmExtensionMove extends CommandBase{
    private ArmExtension extend;
    private double setExtension;

    public ArmExtensionMove(ArmExtension extend, double setExtension) {
        this.extend = extend;
        this.setExtension = setExtension;

        addRequirements(extend);
    }

    @Override
    public void execute() {
        //extend.setExtensionPosition(setExtension);
        extend.setExtensionPos(setExtension);
    }

    @Override
    public boolean isFinished() {
        return extend.isExtensionFinished();
    }
    @Override
    public void end(boolean interrupted){        
        extend.setExtensionMotorOutput(0);
    }
}
