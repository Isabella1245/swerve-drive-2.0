package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmExtension extends SubsystemBase implements Constants{
    public String name; //name of the motor/arm part
    private WPI_TalonSRX armMotor; //motor being used
    private Encoder encoder; //encoder
    PIDController extensionPID;
    boolean extensionPID_Enabled, isExtensionDone;

    public ArmExtension(){
        extensionPID = new PIDController(eP, eI, eD);
        this.name = "arm extension";
        armMotor = new WPI_TalonSRX(extensionMotorID);
        armMotor.setNeutralMode(NeutralMode.Brake);
        encoder = new Encoder(extensionEncoderA, extensionEncoderB);
    }

    //used in TeleopArmExtension
    public void armExtensionControl(double rightY){
        if (rightY > 0.15 && getExtensionEnc() < 8500){
            setExtensionMotorOutput(rightY * 0.7);
        }
        else if (rightY < -0.15 && getExtensionEnc() > -1350){
            setExtensionMotorOutput(rightY * 0.7);
        }
        else {
            setExtensionMotorOutput(0);
        }

        SmartDashboard.putNumber("extension encoder", getExtensionEnc());
    }

    public void setExtensionMotorOutput(double motorOutput) {
        armMotor.set(ControlMode.PercentOutput, motorOutput);
    }

    public double getExtensionEnc(){
        return(double) encoder.getDistance();
    }

    public void setExtensionPosition(double desiredExtensionPosition) {
        double actualExtensionPosition = getExtensionEnc();
        double extensionMotorOutput = extensionPID.calculate(actualExtensionPosition, desiredExtensionPosition);
        double delta = Math.abs(actualExtensionPosition - desiredExtensionPosition);

        if (delta > 50) {
            extensionPID_Enabled = true;
        }
        if (delta < 50 && extensionPID_Enabled) {
            extensionPID_Enabled = false;
        }
        if (extensionMotorOutput != 0) {
            extensionMotorOutput = Math.signum(extensionMotorOutput) * 0.5;
        }
        if (!extensionPID_Enabled) {
            extensionMotorOutput = 0;
        }
        setExtensionMotorOutput(extensionMotorOutput);

        SmartDashboard.putNumber("delta", delta);
        SmartDashboard.putBoolean("is pid enabled", extensionPID_Enabled);
        SmartDashboard.putNumber("extension motor output", extensionMotorOutput);
    }

    public boolean isExtensionFinished() {
        //return extensionPID_Enabled;
        return isExtensionDone;
    }

    //use this if PID is not working within setExtensionPosition method
    public void setExtensionPos(double setExtension) {
        if (setExtension > extensionElevatorMax) { 
            setExtension = extensionElevatorMax;
        }

        if (getExtensionEnc() < (setExtension - 100)) {
            setExtensionMotorOutput(-0.5);
            isExtensionDone = false;
        } else if (getExtensionEnc() > (setExtension - 100) && getExtensionEnc() < (setExtension - 10)) {
            setExtensionMotorOutput(-0.25);
            isExtensionDone = false;


        } else if (getExtensionEnc() > (setExtension + 100)) {
            setExtensionMotorOutput(0.5);
            isExtensionDone = false;
        } else if (getExtensionEnc() < (setExtension + 100) && getExtensionEnc() > (setExtension + 10)) {
            setExtensionMotorOutput(0.25);
            isExtensionDone = false;
        }
        
        else {
            setExtensionMotorOutput(0);
            isExtensionDone = true;
        }
    } 

}
