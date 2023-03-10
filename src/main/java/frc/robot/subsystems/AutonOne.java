package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.TeleopArm;
import frc.robot.subsystems.ArmPart;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.subsystems.SwerveWheelController;
import frc.robot.subsystems.SwerveWheelDrive.SwerveWheelDriveType;
import edu.wpi.first.wpilibj.AnalogInput;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;




public class AutonOne extends SubsystemBase implements Constants {

    private static ArmController instance = null;

    private ArmPart actuatorArm = null;
    private ArmPart extension = null;
    private ArmPart armRotation = null;
    private PneumaticClaw claw = null;
    private Timer mainTimer = new Timer();
    private Timer moveDistanceTimer = new Timer();

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
    
    AHRS gyro = new AHRS(SPI.Port.kMXP);
    

    public AutonOne() {
        
    }

/*     
    SCORE LABELING LOCATION
    Top Cone A    ||   Top Cube B  || Top Cone C
    Middle Cone A || Middle Cube B || Middle Cone C
    Bottom A      ||    Bottom B   || Bottom C
     
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

        //extend arm to Top
       extendArm(topHeight, topExtenstion);
       //place cube (top B),
       placePiece();
       // drive out &retract
       retractArm(retractHeight, retractExtension);
       //moveDistance(p2blueSpeed1, p2blueDriveAngle1, p2blueRamprate1);
       //turn
       turnRobot(frSpinAngle, flSpinAngle, turn180);
       //drive back
       //moveDistance(p2blueDrive2, p2blueDriveAngle2);

    }

    public void BlueAutonPosition1(){
        //blue alliance side, by the substation
        
       //extend arm to Top
       extendArm(topHeight, topExtenstion);
       //place cube (top B),
       placePiece();
       // drive out & turn & retract,
       retractArm(retractHeight, retractExtension);
       //moveDistance(p1BlueSpeed, blueDriveAngle1a);
       turnRobot(frSpinAngle, flSpinAngle, turn180);
       //moveDistance(p1BlueSpeed, blueDriveAngle1b);
       extendArm(floorHeight, floorExtenstion);
       // grab cone from location B3, 
       grabPiece();
       //drive & turn & extend,
       //moveDistance(p1BlueSpeed, blueDriveAngle2a);
       turnRobot(frSpinAngle, flSpinAngle, turn0);
       //moveDistance(p1BlueSpeed, blueDriveAngle2b);
       extendArm(topHeight, topExtenstion); 
       //place cone (top C), 
       placePiece();
       //drive & turn & retract, 
       retractArm(retractHeight, retractExtension);
       //moveDistance(p1BlueSpeed, blueDriveAngle3a);
       turnRobot(frSpinAngle, flSpinAngle, turn180);
       extendArm(floorHeight, floorExtenstion);
       //grab cone from location B4, 
       grabPiece();
       turnRobot(frSpinAngle, flSpinAngle, turn0);
       //drive & turn & extend, 
       //moveDistance(p1BlueSpeed, blueDriveAngle4a);
       //moveDistance(p1BlueSpeed, blueDriveAngle4b);
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
        // drive out & retract,
        retractArm(retractHeight, retractExtension);
        //moveDistance(speed, redDriveAngle1a, ramprate, redTime1a);
        //turn
        turnRobot(frSpinAngle, flSpinAngle, turn180);
        //continue driving out
        //moveDistance(speed, redDriveAngle1b, ramprate, redTime1b);
        extendArm(floorHeight, floorExtenstion);
        // grab cone from location B3, 
        grabPiece();
        //drive back in
        //moveDistance(speed, redDriveAngle2a, ramprate, redTime2a);
        //turn 
        turnRobot(frSpinAngle, flSpinAngle, turn0);
        //drive in and extend
        //moveDistance(speed, redDriveAngle2b, ramprate, redTime2b);
        extendArm(topHeight, topExtenstion); 
        //place cone (top C), 
        placePiece();
        //drive & retract, 
        retractArm(retractHeight, retractExtension);
        //moveDistance(speed, redDriveAngle3a, ramprate, redTime3a);
        //turn
        turnRobot(frSpinAngle, flSpinAngle, turn180);
        //extend
        extendArm(floorHeight, floorExtenstion);
        //grab cone from location B4, 
        grabPiece();
        turnRobot(frSpinAngle, flSpinAngle, turn0);
        //drive & turn & extend, 
        //moveDistance(speed, redDriveAngle4a, ramprate, redTime4a);
        //moveDistance(speed, redDriveAngle4b, ramprate, redTime4b);
        extendArm(topHeight, topExtenstion);
        //place cone (top A)
        placePiece();

    } */

} 