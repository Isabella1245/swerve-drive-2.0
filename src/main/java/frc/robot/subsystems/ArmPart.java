package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;


public class ArmPart extends PIDSubsystem implements Constants {

    //constructors

    //name of the motor/arm part
    public String name;

    //motor being used
    private WPI_TalonSRX armMotor;
    private Encoder encoder;
    public AnalogPotentiometer analogPot;

    //encoder/potentiometer ID
    private int encIDA;
    private int encIDB;
    public int analogId;
    private int analogMax;
    private int AnalogMin;
    public ArmPart(String name, int motorID, int armEncA, int armEncB, int analogPort, int analogMax, int analogMin){

        super(new PIDController(P, I, D));

        this.name = name;
        armMotor = new WPI_TalonSRX(motorID);

        //state encoder or potentiometer
        encoder = new Encoder(armEncA, armEncB);
        analogPot = new AnalogPotentiometer(analogPort, analogMax, analogMin);
        //this was in the other subsystems too idk if we need it
        
        //set name for viewing in smart dashboard
        this.setName(name);

        analogId = analogPort;
        
    }

    //setting speed
    public void setspeed(double speed){
        armMotor.set(speed);
    }
    //find measurements used in calculating the angle or distance for extension
    public double getPot(){
        return(double) analogPot.get();

    }
    public double getArmEnc(){
        return(double) encoder.getDistance();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
		armMotor.set(ControlMode.PercentOutput, output);
	}

    @Override
    protected double getMeasurement() {
        if (analogId == 1) {
            return (double) analogPot.get();
        } else {
            return (double) encoder.getDistance();
        }
    }
    

}