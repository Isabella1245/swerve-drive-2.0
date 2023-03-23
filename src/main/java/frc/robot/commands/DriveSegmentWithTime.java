package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveWheelController;


public class DriveSegmentWithTime extends CommandBase {
    private SwerveWheelController swerve;
    double speed, angle, time;
    boolean interrupted;

    public DriveSegmentWithTime(SwerveWheelController swerve, double speed, double angle, double time) {
        this.swerve = swerve;
        this.speed = speed;
        this.angle = angle;
        this.time = time;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        swerve.driveSegmentOld(speed, angle, time);
    }

    @Override
    public boolean isFinished() {
        boolean result = false;
        if (time > 3) {
            result = true;
        }
        return result;
    }
    @Override
    public void end(boolean interrupted){        
        
    }
    
}