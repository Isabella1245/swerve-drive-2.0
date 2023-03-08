package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveWheelController;


public class AutonDriveSegment extends CommandBase {
    private SwerveWheelController swerve;

    public AutonDriveSegment(SwerveWheelController swerve) {
        this.swerve = swerve;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
       // swerve.;
    }
    
}
