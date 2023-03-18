package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmActuator;

public class ArmActuatorMove extends CommandBase {
    private ArmActuator actuator;
    private double setActuator;

    public ArmActuatorMove(ArmActuator actuator, double setActuator) {
        this.actuator = actuator;
        this.setActuator = setActuator;

        addRequirements(actuator);
    }

    @Override
    public void execute() {
        actuator.setActuatorPosition(setActuator);
    }

    @Override
    public boolean isFinished() {
        return actuator.isActuatorFinished();
    }
    @Override
    public void end(boolean interrupted){        
        actuator.setspeed(0);
    }
}
