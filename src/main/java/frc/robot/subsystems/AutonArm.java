package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.TeleopArm;
import frc.robot.subsystems.ArmPart;
import edu.wpi.first.wpilibj.Timer;


public class AutonArm extends SubsystemBase implements Constants {

    private static ArmController instance = null;

    private ArmPart actuatorArm = null;
    private ArmPart extension = null;
    private ArmPart armRotation = null;
    private PneumaticClaw claw = null;
    private Timer timer;
    

    public AutonArm() {

        actuatorArm = new ArmPart("Arm Angle", actuatorMotorID, 9, 8, analogPotID, analogPotMax, analogPotMin);
        extension = new ArmPart("Arm Extension", extensionMotorID, extensionEncoderA, extensionEncoderB, 7, 10, 10);
        armRotation = new ArmPart("Arm Rotation", clawTwistMotorID, clawEncoderA, clawEncoderB, 6, 10, 10);
        claw = new PneumaticClaw("claw", compressorModule, solenoidChannel, counterChannel);

    }

    /*
    SCORE LABELING LOCATION
    Top Cone A    ||   Top Cube B  || Top Cone C
    Middle Cone A || Middle Cube B || Middle Cone C
    Bottom A      ||    Bottom B   || Bottom C
     */
    public void BlueAutonPosition3(){
        //blue alliance side, by the wall

    }

    public void RedAutonPosition3(){
        //red alliance side, by the wall

    }

    public void BlueAutonPosition2(){
        //blue alliance side, behind the charging station

    }

    public void RedAutonPosition2(){
        //red alliance side, behind the charging station

    }

    public void BlueAutonPosition1(){
        //blue alliance side, by the substation
        
       //extend arm to Top
       extendArm(topHeight, topExtenstion);
       //place cube (top B),
       placePiece();
       // drive out & turn & retract,
       retractArm(retractHeight, retractExtension);
       moveDistance();
       extendArm(floorHeight, floorExtenstion);
       // grab cone from location B3, 
       grabPiece();
       //drive & turn & extend,
       moveDistance();
       extendArm(topHeight, topExtenstion); 
       //place cone (top C), 
       placePiece();
       //drive & turn & retract, 
       retractArm(retractHeight, retractExtension);
       moveDistance();
       extendArm(floorHeight, floorExtenstion);
       //grab cone from location B4, 
       grabPiece();
       //drive & turn & extend, 
       moveDistance();
       extendArm(topHeight, topExtenstion);
       //place cone (top A)
       placePiece();




    }

    public void RedAutonPosition1(){
        //red alliance side, by the substation
        
        //extend arm to Top
        extendArm(topHeight, topExtenstion);
        //place cube (top B),
        placePiece();
        // drive out & turn & retract,
        retractArm(retractHeight, retractExtension);
        moveDistance();
        extendArm(floorHeight, floorExtenstion);
        // grab cone from location B3, 
        grabPiece();
        //drive & turn & extend,
        moveDistance();
        extendArm(topHeight, topExtenstion); 
        //place cone (top C), 
        placePiece();
        //drive & turn & retract, 
        retractArm(retractHeight, retractExtension);
        moveDistance();
        extendArm(floorHeight, floorExtenstion);
        //grab cone from location B4, 
        grabPiece();
        //drive & turn & extend, 
        moveDistance();
        extendArm(topHeight, topExtenstion);
        //place cone (top A)
        placePiece();

    }

    public void placePiece() {
        //lets go of piece
        claw.solenoidSet(false);
    }

    public void extendArm(double potentiometerAngle, double extensionLength) {
        actuatorArm.setSetpoint(potentiometerAngle);
        if (actuatorArm.getPot() > potThreshold) {
        extension.setSetpoint(extensionLength);
        } 
    }

    public void retractArm(double retractAngle, double retractLength) {
        extension.setSetpoint(retractLength);
        if (retractLength < retractThreshold) {
            actuatorArm.setSetpoint(retractAngle);
        }
    }

    public void moveDistance() {

    }

    public void grabPiece() {
        //grabs piece
        claw.solenoidSet(true);

    }
} 