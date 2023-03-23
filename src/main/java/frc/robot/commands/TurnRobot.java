package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveWheelController;

public class TurnRobot extends CommandBase{
    private SwerveWheelController swerve;

    public TurnRobot(SwerveWheelController swerve) {
        this.swerve = swerve;

        addRequirements(swerve);
    }

    @Override
    public void execute(){
        swerve.turnRobot(-52.4, 52.4, 45);
    }
    @Override
    public boolean isFinished(){
        return swerve.isTurnRobotDone();
    }

    @Override
    public void end(boolean interrupted){
        swerve.stop();
    }
}
