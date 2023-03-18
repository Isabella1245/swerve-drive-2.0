package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmWrist extends SubsystemBase implements Constants {
    public String name; //name of the motor/arm part
    private WPI_TalonSRX armMotor; //motor being used
    private Encoder encoder; //encoder
    PIDController wristPID;
    boolean wristPID_Enabled, isWristDone;

    public ArmWrist(){
        wristPID = new PIDController(wP, wI, wD);
        this.name = "arm wrist";
        armMotor = new WPI_TalonSRX(clawTwistMotorID);
        armMotor.setNeutralMode(NeutralMode.Brake);
        encoder = new Encoder(clawEncoderA, clawEncoderB);
    }

    //used in TeleopArmWrist
    public void armWristControl(double leftTrigger, double rightTrigger){
        if (rightTrigger > 0.15 && getWristEnc() < 1500){
            setWristMotorOutput(rightTrigger * 0.3);
        }
        else if (leftTrigger > 0.15 && getWristEnc() > -1220){
            setWristMotorOutput(-leftTrigger * 0.3);
        }else if (leftTrigger < 0.15 && rightTrigger < 0.15) {
            setWristMotorOutput(0.05);
        }

        SmartDashboard.putNumber("wrist encoder", getWristEnc());

    }
    
    public void setWristMotorOutput(double motorOutput) {
        armMotor.set(ControlMode.PercentOutput, motorOutput);
    }

    public double getWristEnc(){
        return(double) encoder.getDistance();
    }

    public void setWristPosition(double desiredWristPosition) {
        double actualWristPosition = getWristEnc();
        double wristMotorOutput = wristPID.calculate(actualWristPosition, desiredWristPosition);
        double delta = Math.abs(actualWristPosition - desiredWristPosition);

        if (delta > 50) {
            wristPID_Enabled = true;
        }
        if (delta < 50 && wristPID_Enabled) {
            wristPID_Enabled = false;
        }
        if (Math.abs(wristMotorOutput) > 50) {
            wristMotorOutput = Math.signum(wristMotorOutput) * 0.25;
        }
        if (!wristPID_Enabled) {
            wristMotorOutput = 0;
        }
        setWristMotorOutput(wristMotorOutput);
    }

    public boolean isWristFinished() {
        //return wristPID_Enabled;
        return isWristDone;
    }

    //use this if PID is not working within setWristPosition method
    public void setWristPos(double setWrist) {
        if (setWrist > wristMax) {
            setWrist = wristMax;
        }

        if (getWristEnc() < setWrist - 25) {
            setWristMotorOutput(-0.5);
            isWristDone = false;
        } else if (getWristEnc() > setWrist + 25) {
            setWristMotorOutput(0.5);
            isWristDone = false;
        } else {
            setWristMotorOutput(0);
            isWristDone = true;
        }
    }    

}
