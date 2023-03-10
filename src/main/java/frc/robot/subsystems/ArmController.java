package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.TeleopArm;
import frc.robot.subsystems.ArmPart;
import edu.wpi.first.wpilibj.Timer;


public class ArmController extends SubsystemBase implements Constants {

    private static ArmController instance = null;

    private ArmPart actuatorArm = null;
    private ArmPart extension = null;
    private ArmPart armRotation = null;
    private PneumaticClaw claw = null;
    private Timer timer;
    

    public ArmController() {

        actuatorArm = new ArmPart("Arm Angle", actuatorMotorID, 9, 8, analogPotID, analogPotMax, analogPotMin);
        extension = new ArmPart("Arm Extension", extensionMotorID, extensionEncoderA, extensionEncoderB, 7, 10, 10);
        armRotation = new ArmPart("Arm Rotation", clawTwistMotorID, clawEncoderA, clawEncoderB, 6, 10, 10);
        claw = new PneumaticClaw("claw", compressorModule, solenoidChannel, counterChannel);

    }

    /*                  controls for arm
     *   ------------------------------------------------
     *               type : xbox controller
     *      arm angle (linear actuator) : left Y axis
     *             arm extention : right Y axis
     *           arm rotation : triggers i think
     */

    
    public void armControl(double leftY, double rightY, double leftTrigger, double rightTrigger, boolean aButton, boolean xButton, boolean yButton, boolean bButton) {

        boolean ybutton = false;
        //here is where we'll need to calculate angle and distance and rotation.
        //the angle sould calculate somewhere between like 0 to a little over 90 degrees, but
        //it will be intersting because out potentiometer is not calculating the angle, but its actually calculating
        //the distance the actuator is pushing on the arm to lift it.
        //i dont think we'll need to measure the turning one.
        //and we might want to measure the distance but idk.

        //actuator code
        if (leftY > 0.15 && actuatorArm.getPot() < 3000){
            actuatorArm.setspeed(leftY * -0.7);
        }
        else if (leftY < -0.15 && actuatorArm.getPot() > 240){
            actuatorArm.setspeed(leftY * -0.7);
        } 
        else if (leftY < -0.15 && actuatorArm.getPot() < 240){
            actuatorArm.setspeed(-0.7);
        } 
        //mid extension height button
        else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() < thresholdHeight){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() < midExtenstion){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.4);

        } else if (bButton && actuatorArm.getPot() >= midHeight && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() < midExtenstion){
            extension.setspeed(0.4);

        } else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() >= midExtenstion){
            actuatorArm.setspeed(-0.8);
            extension.setspeed(0.05);
        //top extensions height button
        } else if (yButton && actuatorArm.getPot() < topHeight1 && actuatorArm.getPot() < thresholdHeight){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (yButton && actuatorArm.getPot() < topHeight1 && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() < topExtenstion){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(-0.4);

        } else if (yButton && actuatorArm.getPot() >= topHeight1 && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() < topExtenstion){
            extension.setspeed(-0.4);
            actuatorArm.setspeed(0);

        } else if (yButton && actuatorArm.getPot() < topHeight1 && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() >= topExtenstion){
            actuatorArm.setspeed(-0.8);
            extension.setspeed(0.05);

        } else {
            actuatorArm.setspeed(0);
            extension.setspeed(0.05);
        }

        double position = 0;
        // arm extension code
        if (rightY > 0.15 && extension.getArmEnc() < 8500){
            extension.setspeed(rightY * 0.7);
            position = extension.getArmEnc();
        }
        else if (rightY < -0.15 && extension.getArmEnc() > -1250){
            extension.setspeed(rightY * 0.7);
            position = extension.getArmEnc();
        }
        else if (rightY > -0.15 && rightY < 0.15){
            extension.setspeed(0.1);
        }

        //claw wrist
        if (rightTrigger > 0.15 && armRotation.getArmEnc() < 1500){
            armRotation.setspeed(rightTrigger * 0.4);
        }
        else if (leftTrigger > 0.15 && armRotation.getArmEnc() > -1220){
            armRotation.setspeed(-leftTrigger * 0.4);
        }else{
            armRotation.setspeed(0.05);
        }

        //pneumatics
        if (xButton) {
            claw.solenoidSet(true);
        }

        if (aButton) {
            claw.solenoidSet(false);

        }

    SmartDashboard.putNumber("Extension Encoder",extension.getArmEnc());
    SmartDashboard.putNumber("Actuator Potentiometer",actuatorArm.getPot());
    SmartDashboard.putNumber("Arm Rotation",armRotation.getArmEnc());

    }


    //AUTON STUFF
    public void setActuatorArmPos(double setHeight) {
        if (actuatorArm.getPot() < setHeight - 10) {
            actuatorArm.setspeed(-0.8);
        } else if (actuatorArm.getPot() > setHeight + 10) {
            actuatorArm.setspeed(0.8);
        }
    } 

    public void setExtensionPos(double setExtension) {
        if (extension.getArmEnc() < setExtension - 25) {
            extension.setspeed(-0.4);
        } else if (extension.getArmEnc() > setExtension + 25) {
            extension.setspeed(0.4);
        }
    } 

    public void setArmRotationPos(double setWrist) {
       
        /*if (armRotation.getArmEnc() < setWrist - 25) {
            armRotation.setspeed(-0.3);
        } else if (armRotation.getArmEnc() > setWrist + 25) {
            armRotation.setspeed(0.3);
        }*/

        armRotation.setArmPosition(setWrist);
    } 

    public void setGamePiece(double setHeight, double setExtension, double setWrist) {
        if (actuatorArm.getPot() < setHeight && actuatorArm.getPot() < thresholdHeight){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (actuatorArm.getPot() < setHeight && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() < setExtension){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(-0.4);

        } else if (actuatorArm.getPot() >= setHeight && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() < setExtension){
            extension.setspeed(-0.4);
            actuatorArm.setspeed(0);

        } else if (actuatorArm.getPot() < setHeight && actuatorArm.getPot() > thresholdHeight && extension.getArmEnc() >= setExtension){
            actuatorArm.setspeed(-0.8);
            extension.setspeed(0.05);

        } else if (actuatorArm.getPot() >= setHeight && extension.getArmEnc() >= setExtension && armRotation.getArmEnc() < setWrist) {
            actuatorArm.setspeed(0);
            extension.setspeed(0);
            //tried negative speed
            armRotation.setspeed(-0.3);
        }
        else {
            actuatorArm.setspeed(0);
            extension.setspeed(0.05);
            //tried positive speed 
            armRotation.setspeed(0.2);
        }


    }

    //auton claw buttons
    public void openClaw() {
            claw.solenoidSet(true);
    }
    public void closeClaw() {
            claw.solenoidSet(false);
    }
    





    //doing the instance thing that the vikings were doing
    //returns the instance
    public static ArmController getInstance() {
        if (instance == null) {
            instance = new ArmController();
            instance.setDefaultCommand(new TeleopArm());
        }

        return instance;
    }

    public void stop(){
        actuatorArm.setspeed(0);
        extension.setspeed(0);
        armRotation.setspeed(0);
    }
}