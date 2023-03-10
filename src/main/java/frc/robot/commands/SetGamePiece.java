package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmController;


public class SetGamePiece extends CommandBase{
    private ArmController arm;
    double height, extension, wrist;

    public SetGamePiece(ArmController arm, double height, double extension, double wrist) {
        this.arm = arm;
        this.height = height;
        this.extension = extension;
        this.wrist = wrist;
        addRequirements(arm);
    }

    @Override
    public void execute(){
        arm.setGamePiece(height, extension, wrist);
    }

     // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
