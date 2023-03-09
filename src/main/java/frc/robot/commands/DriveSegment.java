package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveWheelController;


public class DriveSegment extends CommandBase {
    private SwerveWheelController swerve;
    double speed, angle, time;

    public DriveSegment(SwerveWheelController swerve, double speed, double angle, double time) {
        this.swerve = swerve;
        this.speed = speed;
        this.angle = angle;
        this.time = time;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        swerve.driveSegment(speed, angle, time);
    }
    
}
