package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.TeleopArm;
import frc.robot.subsystems.ArmPart;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.SwerveWheelController;
import frc.robot.subsystems.SwerveWheelDrive.SwerveWheelDriveType;
import edu.wpi.first.wpilibj.AnalogInput;


public class Auton extends SubsystemBase implements Constants {

    private static ArmController instance = null;

    private ArmPart actuatorArm = null;
    private ArmPart extension = null;
    private ArmPart armRotation = null;
    private PneumaticClaw claw = null;
    private Timer timer;

    private SwerveWheelDrive frontRightDrive = null;
    private SwerveWheelDrive frontLeftDrive = null;
    private SwerveWheelDrive backRightDrive = null;
    private SwerveWheelDrive backLeftDrive = null;

    //swerve wheels
    private SwerveWheel frontRight = null;
    private SwerveWheel frontLeft = null;
    private SwerveWheel backRight = null;
    private SwerveWheel backLeft = null;

    AnalogInput FRDenc = new AnalogInput(FRDencoderID);
    

    public Auton() {

        //actuatorArm = new ArmPart("Arm Angle", actuatorMotorID, 9, 8, analogPotID, analogPotMax, analogPotMin);
        //extension = new ArmPart("Arm Extension", extensionMotorID, extensionEncoderA, extensionEncoderB, 7, 10, 10);
        //armRotation = new ArmPart("Arm Rotation", clawTwistMotorID, clawEncoderA, clawEncoderB, 6, 10, 10);
        //claw = new PneumaticClaw("claw", compressorModule, solenoidChannel, counterChannel);

        //frontRightDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, FRDid, true, FRDencoderID);
        //frontLeftDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, FLDid, false, 6);
        //backRightDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, BRDid, true, 7);
        //backLeftDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, BLDid, false, 8);
        //potential robot container content
        frontRight = new SwerveWheel(frontRightDrive, FRTid, FRTencoderID, FRTencoderOffset, "Front Right", true);
        frontLeft = new SwerveWheel(frontLeftDrive, FLTid, FLTencoderID, FLTencoderOffset, "Front Left", true);
        backRight = new SwerveWheel(backRightDrive, BRTid, BRTencoderID, BRTencoderOffset, "Back Right", true);
        backLeft = new SwerveWheel(backLeftDrive, BLTid, BLTencoderID, BLTencoderOffset, "Back Left", true);
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
       //moveDistance();
       extendArm(floorHeight, floorExtenstion);
       // grab cone from location B3, 
       grabPiece();
       //drive & turn & extend,
       //moveDistance();
       extendArm(topHeight, topExtenstion); 
       //place cone (top C), 
       placePiece();
       //drive & turn & retract, 
       retractArm(retractHeight, retractExtension);
       //moveDistance();
       extendArm(floorHeight, floorExtenstion);
       //grab cone from location B4, 
       grabPiece();
       //drive & turn & extend, 
       //moveDistance();
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
        moveDistance(blueDrive1a, blueDriveAngle1a);
        //turnRobot();
        moveDistance(blueDrive1b, blueDriveAngle1b);
        extendArm(floorHeight, floorExtenstion);
        // grab cone from location B3, 
        grabPiece();
        //drive & turn & extend,
        //moveDistance();
        extendArm(topHeight, topExtenstion); 
        //place cone (top C), 
        placePiece();
        //drive & turn & retract, 
        retractArm(retractHeight, retractExtension);
        //moveDistance();
        extendArm(floorHeight, floorExtenstion);
        //grab cone from location B4, 
        grabPiece();
        //drive & turn & extend, 
        //moveDistance();
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

    public void moveDistance(double distance, double angle) {

    }

    public void grabPiece() {
        //grabs piece
        claw.solenoidSet(true);

    }
    
    public void turnRobot(double frAngle, double flAngle, double speed, double turnDistance) {
        
    }

    public double getDriveEnc(){
		return (double) FRDenc.getValue();
	}
} 