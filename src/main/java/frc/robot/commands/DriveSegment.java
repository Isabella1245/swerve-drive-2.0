package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveWheelController;


public class DriveSegment extends CommandBase {
    private SwerveWheelController swerve;
    double speed, angle, time;
    Timer timer = new Timer();
    boolean isDriveFinished = false;

    public DriveSegment(SwerveWheelController swerve, double speed, double angle, double time) {
        this.swerve = swerve;
        this.speed = speed;
        this.angle = angle;
        this.time = time;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        timer.reset();
        timer.start();
        swerve.driveSegment(speed, angle);
        if(timer.hasElapsed(time)){
            isDriveFinished = true;
        }
        else {
            isDriveFinished = false;
        }
    //if drive doesnt stop in auton after stated seconds move if statement under
    //isFinished method and return isDriveFinished
    //if none of them work then use .withTimeout 
    }

    @Override
    public boolean isFinished() {
        return isDriveFinished;
    }

    @Override
    public void end(boolean interrupted){
        swerve.stop();
    }
    
    
}
