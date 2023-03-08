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
        else if (leftY < -0.15 && actuatorArm.getPot() > 200){
            actuatorArm.setspeed(leftY * -0.7);
        } 
        else if (leftY < -0.15 && actuatorArm.getPot() < 200){
            actuatorArm.setspeed(-0.7);
        } 
        //mid extension height button
        else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() < potThreshold){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < midExtenstion){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.4);

        } else if (bButton && actuatorArm.getPot() >= midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < midExtenstion){
            extension.setspeed(0.4);

        } else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() >= midExtenstion){
            actuatorArm.setspeed(-0.8);
            extension.setspeed(0.05);
        //top extensions height button
        } else if (yButton && actuatorArm.getPot() < topHeight && actuatorArm.getPot() < potThreshold){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (yButton && actuatorArm.getPot() < topHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < topExtenstion){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(-0.4);

        } else if (yButton && actuatorArm.getPot() >= topHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < topExtenstion){
            extension.setspeed(-0.4);
            actuatorArm.setspeed(0);

        } else if (yButton && actuatorArm.getPot() < topHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() >= topExtenstion){
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
        else if (rightY < -0.15 && extension.getArmEnc() > -1200){
            extension.setspeed(rightY * 0.7);
            position = extension.getArmEnc();
        }
        else if (rightY > -0.15 && rightY < 0.15){
            extension.setspeed(0.1);
        }

        if (rightTrigger > 0.15 && armRotation.getArmEnc() < 1024){
            armRotation.setspeed(rightTrigger * 0.7);
        }
        else if (leftTrigger > 0.15 && armRotation.getArmEnc() > -1200){
            armRotation.setspeed(-leftTrigger * 0.7);
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
    //angle & extension button FLOOR HEIGHT
    public void floorHeightArm() {
        if (actuatorArm.getPot() < midHeight && actuatorArm.getPot() < potThreshold){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (actuatorArm.getPot() < midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < midExtenstion){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.4);

        } else if (actuatorArm.getPot() >= midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < midExtenstion){
            extension.setspeed(0.4);
            actuatorArm.setspeed(0);

        } else if (actuatorArm.getPot() < midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() >= midExtenstion){
            actuatorArm.setspeed(-0.8);
            extension.setspeed(0.05);
        } else {
            actuatorArm.setspeed(0);
            extension.setspeed(0.05);
        }
    } 

    //angle & extension button MID HEIGHT
    public void midHeightArm(boolean bButton) {
        if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() < potThreshold){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < midExtenstion){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.4);

        } else if (bButton && actuatorArm.getPot() >= midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < midExtenstion){
            extension.setspeed(0.4);
            actuatorArm.setspeed(0);

        } else if (bButton && actuatorArm.getPot() < midHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() >= midExtenstion){
            actuatorArm.setspeed(-0.8);
            extension.setspeed(0.05);
        } else {
            actuatorArm.setspeed(0);
            extension.setspeed(0.05);
        }
    } 

    //angle & extension button TOP HEIGHT
    public void topHeightArm(boolean yButton) {
        if (yButton && actuatorArm.getPot() < topHeight && actuatorArm.getPot() < potThreshold){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(0.05);

        } else if (yButton && actuatorArm.getPot() < topHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < topExtenstion){
            actuatorArm.setspeed(-0.8); 
            extension.setspeed(-0.4);

        } else if (yButton && actuatorArm.getPot() >= topHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() < topExtenstion){
            extension.setspeed(-0.4);
            actuatorArm.setspeed(0);

        } else if (yButton && actuatorArm.getPot() < topHeight && actuatorArm.getPot() > potThreshold && extension.getArmEnc() >= topExtenstion){
            actuatorArm.setspeed(-0.8);
            extension.setspeed(0.05);

        } else {
            actuatorArm.setspeed(0);
            extension.setspeed(0.05);
        }
    }

    //auton claw buttons
    public void OpenClaw() {
            claw.solenoidSet(true);
    }
    public void CloseClaw() {
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