package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveWheelController;

public class BalanceRobot extends CommandBase {
    private SwerveWheelController swerve;

    public BalanceRobot(SwerveWheelController swerve) {
        this.swerve = swerve;
        
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        swerve.balanceRobot();
    }

    @Override
    public boolean isFinished(){
        return swerve.isBalanceDone();
    }

    @Override
    public void end(boolean interrupted){
        swerve.stop();
    }
    
}
