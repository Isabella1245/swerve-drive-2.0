package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmActuator extends SubsystemBase implements Constants {
    public String name; //name of the motor/arm part
    private WPI_TalonSRX armMotor; //motor being used
    public AnalogPotentiometer analogPot; //analog potentiometer
    public int analogId; //analog numbers
    boolean isActuatorDone = false;

    public ArmActuator(){
        this.name = "actuator";
        armMotor = new WPI_TalonSRX(actuatorMotorID);
        //create analog potentiometer
        analogPot = new AnalogPotentiometer(analogPotID, analogPotMax, analogPotMin);
        //set name for viewing in smart dashboard
        this.setName(name);
    }

    //used in TeleopArmActuator
    public void armActuatorControl(double leftY){
        if (leftY < -0.15 && getPot() < analogPotMax){
            setspeed(leftY);
        }
        else if (leftY > 0.15 && getPot() > analogPotMin){
            setspeed(leftY);
        } 
        else {
            setspeed(0);
        }

        SmartDashboard.putNumber("actuator potentiometer", getPot());
        SmartDashboard.putBoolean("is actuator done", isActuatorFinished());

    }

    //setting speed
    public void setspeed(double speed){
        armMotor.set(speed);
    }
    //find measurements used in calculating the angle or distance for extension
    public double getPot(){
        return(double) analogPot.get();
    }

    public void setActuatorPosition(double setHeight) {

        if (setHeight > analogPotMax) {
            setHeight = analogPotMax;
        }

        if (getPot() < setHeight - 3) {
            setspeed(-0.8);
            isActuatorDone = false;
        } else if (getPot() > setHeight + 3) {
            setspeed(0.8);
            isActuatorDone = false;
        } else {
            setspeed(0);
            isActuatorDone = true;
        }

    }

    public boolean isActuatorFinished() {
        return isActuatorDone;
    }

    

}
