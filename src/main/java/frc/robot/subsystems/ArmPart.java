package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;


public class ArmPart extends SubsystemBase implements Constants {

    //constructors

    //name of the motor/arm part
    public String name;

    //motor being used
    private WPI_TalonSRX armMotor;

    //encoder/potentiometer ID
    private int encID;

    public ArmPart(String name, int motorID, int armEnc){

        this.name = name;
        armMotor = new WPI_TalonSRX(motorID);

        //state encoder or potentiometer

        //this was in the other subsystems too idk if we need it
        /*
         *set name for viewing in smart dashboard
        this.setName(name);
         */
    }

    //setting speed
    public void setspeed(double speed){
        armMotor.set(speed);
    }
    //find measurements used in calculating the angle or distance for extension


}