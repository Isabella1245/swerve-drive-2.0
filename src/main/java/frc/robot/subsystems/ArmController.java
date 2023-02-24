package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.TeleopArm;
import frc.robot.subsystems.ArmPart;


public class ArmController extends SubsystemBase implements Constants {

    private static ArmController instance = null;

    private ArmPart actuatorArm = null;
    private ArmPart extension = null;
    private ArmPart armRotation = null;
    private PneumaticClaw claw = null;

    public ArmController() {

        actuatorArm = new ArmPart("Arm Angle", actuatorMotorID, analogPotID);
        extension = new ArmPart("Arm Extension", extensionMotorID, 5);
        armRotation = new ArmPart("Arm Rotation", clawTwistMotorID, 5);
        claw = new PneumaticClaw("claw", compressorModule, solenoidChannel, counterChannel);

    }

    /*                  controls for arm
     *   ------------------------------------------------
     *               type : xbox controller
     *      arm angle (linear actuator) : left Y axis
     *             arm extention : right Y axis
     *           arm rotation : triggers i think
     */

    
    public void armControl(double leftY, double rightY, double leftTrigger, double rightTrigger, boolean aButton, boolean xButton) {

        //here is where we'll need to calculate angle and distance and rotation.
        //the angle sould calculate somewhere between like 0 to a little over 90 degrees, but
        //it will be intersting because out potentiometer is not calculating the angle, but its actually calculating
        //the distance the actuator is pushing on the arm to lift it.
        //i dont think we'll need to measure the turning one.
        //and we might want to measure the distance but idk.

        //pneumatic claw
        if (xButton) {
            claw.solenoidSet(false);
        }
        if (aButton) {
            claw.solenoidSet(true);
        }


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