package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.SwerveWheelDrive.SwerveWheelDriveType;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SwerveWheel;
import edu.wpi.first.wpilibj.Timer;


import com.kauailabs.navx.frc.AHRS;

public class SwerveWheelController extends SubsystemBase implements Constants  {
    
    private static SwerveWheelController instance = null;
    
    //stating the drive motors
    public SwerveWheelDrive frontRightDrive = null;
    public SwerveWheelDrive frontLeftDrive = null;
    public SwerveWheelDrive backRightDrive = null;
    public SwerveWheelDrive backLeftDrive = null;

    //swerve wheels
    public SwerveWheel frontRight = null;
    public SwerveWheel frontLeft = null;
    public SwerveWheel backRight = null;
    public SwerveWheel backLeft = null;

    public AHRS gyro = null;

    Timer moveDistanceTimer = new Timer();


    // Get distance between wheels
    public double r = Math.sqrt((L * L) + (W * W));

    public boolean isFieldCentric = true;
    public boolean gyroEnabled = true;

    public SwerveWheelController(){
    
        //the motor controls are all talon srx
        //drive motors
        //potential robot container content
        frontRightDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, FRDid, true);
        frontLeftDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, FLDid, false);
        backRightDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, BRDid, true);
        backLeftDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, BLDid, false);
        //potential robot container content
        frontRight = new SwerveWheel(frontRightDrive, FRTid, FRTencoderID, FRTencoderOffset, "Front Right", true);
        frontLeft = new SwerveWheel(frontLeftDrive, FLTid, FLTencoderID, FLTencoderOffset, "Front Left", true);
        backRight = new SwerveWheel(backRightDrive, BRTid, BRTencoderID, BRTencoderOffset, "Back Right", true);
        backLeft = new SwerveWheel(backLeftDrive, BLTid, BLTencoderID, BLTencoderOffset, "Back Left", true);
        

        try {
            gyro = new AHRS(SPI.Port.kMXP); 
            gyroEnabled = true;
        } catch (RuntimeException ex ) {
            System.out.println("--------------");
            System.out.println("NavX not plugged in");
            System.out.println("--------------");
            gyroEnabled = false;
        }

        frontRight.enable();
        frontLeft.enable();
        backRight.enable();
        backLeft.enable();

        //frontRight.resetTurnMotors();
        //frontLeft.resetTurnMotors();
        //backRight.resetTurnMotors();
        //backLeft.resetTurnMotors();
    }

         // x = strafe, y = speed, z = rotation 
    // Holonomic drive
    public void drive(double x, double y, double z, double gyroValue, boolean rBumper, boolean lBumper) {

        if (lBumper) {
            y *= 0.3;
            x *= 0.3;
        }
        
        if (rBumper) {
            y *= 1.6;
            x *= 1.6;
        }
        //inverts y for drive
        y *= -1;
        //gves max speed
        y *= 0.50;
        x *= 0.50;

        //caps z at 0.2
        z *= 0.3;

        //calculate magnitude
        double magnitude = Math.sqrt((Math.pow(x, 2)) + (Math.pow(y,2)) + Math.pow(z,2));
        
        double frontLeftSpeed = 0;
        double frontRightSpeed = 0;
        double backRightSpeed = 0;
        double backLeftSpeed = 0;

        double frontLeftAngle = 0;
        double frontRightAngle = 0;
        double backLeftAngle = 0;
        double backRightAngle = 0;

   
        if (magnitude >= 0.1) {

            gyroValue -= 180;
            // I got this bit of code from the NavX website
            if (isFieldCentric == true && gyroEnabled == true) {
                // Convert gyro angle to radians
                double gyro = gyroValue * Math.PI / 180;

                //field orientation math
                double temp = y * Math.cos(gyro) + x * Math.sin(gyro); 
                x = -y * Math.sin(gyro) + x * Math.cos(gyro); 
                y = temp;
            }

            // -------------------------------------
            // Do the swerve wheel math for speed and angles
            // -------------------------------------
            double a;
            double b;
            double c;
            double d;

            //if (z > 0.2 || z < -0.2){
            a = x - z * (L / r);
            b = x + z * (L / r);
            c = y - z * (W / r);
            d = y + z * (W / r);
            /* } else {
                a = x - (L / r);
                b = x + (L / r);
                c = y - (W / r);
                d = y + (W / r);
            }*/

            /* 
            frontLeftSpeed = Math.sqrt((b * b) + (c * c));
            frontRightSpeed = Math.sqrt((b * b) + (d * d));
            backRightSpeed = Math.sqrt((a * a) + (d * d));
            backLeftSpeed = Math.sqrt((a * a) + (c * c));

            backRightAngle = Math.atan2(a, d) * 180 / Math.PI;
            backLeftAngle = Math.atan2(a, c) * 180 / Math.PI;
            frontRightAngle = Math.atan2(b, d) * 180 / Math.PI;
            frontLeftAngle = Math.atan2(b, c) * 180 / Math.PI ;  
            */ 

            //apparently the c and d parts for fr/l and br/l were mixed up
            //those silly vikings!!! XD
            
            frontRightSpeed = Math.sqrt((b * b) + (c * c));
            frontLeftSpeed = Math.sqrt((b * b) + (d * d));
            backLeftSpeed = Math.sqrt((a * a) + (d * d));
            backRightSpeed = Math.sqrt((a * a) + (c * c));

            frontRightAngle = Math.atan2(b, c) * 180 / Math.PI;
            frontLeftAngle = Math.atan2(b, d) * 180 / Math.PI; 
            backLeftAngle = Math.atan2(a, d) * 180 / Math.PI;
            backRightAngle = Math.atan2(a, c) * 180 / Math.PI;



            

            // This bit of code normalizes the speed
            double max = frontLeftSpeed;
            max = Math.max(max, frontRightSpeed);
            max = Math.max(max, backRightSpeed);
            max = Math.max(max, backLeftSpeed);

            if (max > 1) {
                frontLeftSpeed /= max;
                frontRightSpeed /= max;
                backRightSpeed /= max;
                backLeftSpeed /= max;
            }

            /* */


            //Q1 and Q2
            frontRight.setSetpoint(frontRightAngle);
            frontLeft.setSetpoint(frontLeftAngle);
            backRight.setSetpoint(backRightAngle);
            backLeft.setSetpoint(backLeftAngle);
            frontLeft.setSpeed(frontLeftSpeed);
            frontRight.setSpeed(frontRightSpeed);
            backRight.setSpeed(backRightSpeed);
            backLeft.setSpeed(backLeftSpeed);

          
        } 
        else {
            frontLeft.setSpeed(0);
            frontRight.setSpeed(0);
            backRight.setSpeed(0);
            backLeft.setSpeed(0);
        }

        //SmartDashboard.putNumber("magnitude", magnitude);
        /*
        SmartDashboard.putNumber("fr angle", frontRight.getSetpoint());
        SmartDashboard.putNumber("fl angle", frontLeft.getSetpoint());
        SmartDashboard.putNumber("br angle", backRight.getSetpoint());
        SmartDashboard.putNumber("bl angle", backLeft.getSetpoint());
        SmartDashboard.putNumber("gyro", gyroValue);
        SmartDashboard.putNumber("fl speed", frontLeftSpeed);
        SmartDashboard.putNumber("fr speed", frontRightSpeed);
        SmartDashboard.putNumber("Bl speed", backLeftSpeed);
        SmartDashboard.putNumber("br speed", backRightSpeed);
        */
        SmartDashboard.putNumber("fl angle", frontLeftAngle);
        SmartDashboard.putNumber("fr angle", frontRightAngle);
        SmartDashboard.putNumber("Bl angle", backLeftAngle);
        SmartDashboard.putNumber("br angle", backRightAngle);
        
        SmartDashboard.putNumber("gyro balance", gyro.getRoll());
        //SmartDashboard.putData("pid", SubsystemBase.);

        //front right
        SmartDashboard.putNumber("FR AbsEnc Value", frontRight.getAbsEncValue());
        SmartDashboard.putNumber("FR AbsEnc Angle ", frontRight.getAbsAngleDeg());
        SmartDashboard.putNumber("FR QuadEnc Actual Value", frontRight.getTicks());
        SmartDashboard.putNumber("FR QuadEnc Angle", frontRight.getMeasurement());
        SmartDashboard.putNumber("FR Turn Motor speed", frontRight.getTurnMotorSpeed());
        //SmartDashboard.putNumber("p values", frontRight.getPValues());
        //SmartDashboard.putNumber("i values", frontRight.getIValues());
        //SmartDashboard.putNumber("d values", frontRight.getDValues());

        //SmartDashboard.putNumber("position tolerance", frontRight.getTolerance());
        //SmartDashboard.putNumber("position error", frontRight.getError());
        //SmartDashboard.putNumber("calculation", frontRight.getCalculation());

        //SmartDashboard.putNumber("pid output", frontRight.getOutput());

        //SmartDashboard.putNumber("init value", frontLeft.getInitialPgValue());

        //front left
        SmartDashboard.putNumber("FL AbsEnc Value", frontLeft.getAbsEncValue());
        SmartDashboard.putNumber("FL AbsEnc Angle ", frontLeft.getAbsAngleDeg());
        SmartDashboard.putNumber("FL QuadEnc Actual Value", frontLeft.getTicks());
        SmartDashboard.putNumber("FL QuadEnc Angle", frontLeft.getMeasurement());
        SmartDashboard.putNumber("FL Turn Motor speed", frontLeft.getTurnMotorSpeed());

        //back right
        SmartDashboard.putNumber("BR AbsEnc Value", backRight.getAbsEncValue());
        SmartDashboard.putNumber("BR AbsEnc Angle ", backRight.getAbsAngleDeg());
        SmartDashboard.putNumber("BR QuadEnc Actual Value", backRight.getTicks());
        SmartDashboard.putNumber("BR QuadEnc Angle", backRight.getMeasurement());
        SmartDashboard.putNumber("BR Turn Motor speed", backRight.getTurnMotorSpeed());

        //back left
        SmartDashboard.putNumber("BL AbsEnc Value", backLeft.getAbsEncValue());
        SmartDashboard.putNumber("BL AbsEnc Angle ", backLeft.getAbsAngleDeg());
        SmartDashboard.putNumber("BL QuadEnc Actual Value", backLeft.getTicks());
        SmartDashboard.putNumber("BL QuadEnc Angle", backLeft.getMeasurement());
        SmartDashboard.putNumber("BL Turn Motor speed", backLeft.getTurnMotorSpeed());
        //SmartDashboard.putNumber("ticks to angle", frontRight.ticksToAngle());
    }

    


    //AUTON STUFF
    public void turnRobot(double frAngle, double flAngle, double turnAngle) {
        frontRight.setSetpoint(frAngle);
        frontLeft.setSetpoint(flAngle);
        backRight.setSetpoint(flAngle);
        backLeft.setSetpoint(frAngle);

        if (turnAngle > gyro.getYaw()) {
            if (gyro.getYaw() < turnAngle/2) {
                frontRight.setSpeed(-((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))-0.1));
                frontLeft.setSpeed(((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))+0.1));
                backRight.setSpeed(-((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))-0.1));
                backLeft.setSpeed(((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))+0.1));
            } else if (gyro.getYaw() > turnAngle/2 ) {
                frontRight.setSpeed(-((1.1/(1+Math.exp((gyro.getYaw()) - 177)))-0.1));
                frontLeft.setSpeed(((1.1/(1+Math.exp((gyro.getYaw()) - 177)))+0.1));
                backRight.setSpeed(-((1.1/(1+Math.exp((gyro.getYaw()) - 177)))-0.1));
                backLeft.setSpeed(((1.1/(1+Math.exp((gyro.getYaw()) - 177)))+0.1));
            }   
        } else if (turnAngle < gyro.getYaw()) {
            if (gyro.getYaw() > 90) {
                frontRight.setSpeed(((1.1/(1+Math.exp((gyro.getYaw()) - 177)))+0.1));
                frontLeft.setSpeed(-((1.1/(1+Math.exp((gyro.getYaw()) - 177)))-0.1));
                backRight.setSpeed(((1.1/(1+Math.exp((gyro.getYaw()) - 177)))+0.1));
                backLeft.setSpeed(-((1.1/(1+Math.exp((gyro.getYaw()) - 177)))-0.1));
            } else if (gyro.getYaw() < 90 ) {
                frontRight.setSpeed(((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))+0.1));
                frontLeft.setSpeed(-((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))+0.1));
                backRight.setSpeed(((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))+0.1));
                backLeft.setSpeed(-((0.95/(1+Math.exp(-(gyro.getYaw()) + 4)))+0.1));
            }
        } else {
            frontRight.setSpeed(0);
            frontLeft.setSpeed(0);
            backRight.setSpeed(0);
            backLeft.setSpeed(0);

        }
    }

    public void driveSegmentOld(double speed, double angle, double timeSeconds) {
        moveDistanceTimer.reset();
        moveDistanceTimer.start();

        //frontRight.setSetpoint(angle);
        //frontLeft.setSetpoint(angle);
        backRight.setSetpoint(angle);
        backLeft.setSetpoint(angle);

        if(moveDistanceTimer.get() < timeSeconds) {
            //frontRight.setSpeed(speed);
            //frontLeft.setSpeed(speed);
            backRight.setSpeed(speed);
            backLeft.setSpeed(speed);
            
            //frontRight.setSetpoint(angle);
            //frontLeft.setSetpoint(angle);
            backRight.setSetpoint(angle);
            backLeft.setSetpoint(angle);
        } else {
            //frontRight.setSpeed(0);
            //frontLeft.setSpeed(0);
            backRight.setSpeed(0);
            backLeft.setSpeed(0);
            moveDistanceTimer.stop();
        }

    }

    public void driveSegment(double speed, double angle) {
        frontRight.setSetpoint(angle);
        frontLeft.setSetpoint(angle);
        backRight.setSetpoint(angle);
        backLeft.setSetpoint(angle);

        frontRight.setSpeed(speed);
        frontLeft.setSpeed(speed);
        backRight.setSpeed(speed);
        backLeft.setSpeed(speed);
    }

    

    public boolean isGyroReqMet() {
        boolean gyroReqMet;
        if (Math.abs(gyro.getRoll()) > 15) {
            gyroReqMet = true;
        } else {
            gyroReqMet = false;
        }
        return gyroReqMet;
    }

    public void balanceRobot() {

        if(Math.abs(gyro.getRoll()) > 25) {
            frontRight.setSpeed(dockRampSpeed * Math.signum(gyro.getRoll()));
            frontLeft.setSpeed(dockRampSpeed * Math.signum(gyro.getRoll()));
            backRight.setSpeed(dockRampSpeed * Math.signum(gyro.getRoll()));
            backLeft.setSpeed(dockRampSpeed * Math.signum(gyro.getRoll()));

        } else if (Math.abs(gyro.getRoll()) > dockPlatformAngle1 && gyro.getRoll() < 25) {
            frontRight.setSpeed(dockPlatformSpeed1 * Math.signum(gyro.getRoll()));
            frontLeft.setSpeed(dockPlatformSpeed1 * Math.signum(gyro.getRoll()));
            backRight.setSpeed(dockPlatformSpeed1 * Math.signum(gyro.getRoll()));
            backLeft.setSpeed(dockPlatformSpeed1 * Math.signum(gyro.getRoll()));

        } else if (Math.abs(gyro.getRoll()) < dockPlatformAngle1 && gyro.getRoll() > 8){
            frontRight.setSpeed(dockPlatformSpeed2 * Math.signum(gyro.getRoll()));
            frontLeft.setSpeed(dockPlatformSpeed2 * Math.signum(gyro.getRoll()));
            backRight.setSpeed(dockPlatformSpeed2 * Math.signum(gyro.getRoll()));
            backLeft.setSpeed(dockPlatformSpeed2 * Math.signum(gyro.getRoll()));

        } else if (Math.abs(gyro.getRoll()) < 8 && gyro.getRoll() > 3) {
            frontRight.setSpeed(dockPlatformSpeed3 * Math.signum(gyro.getRoll()));
            frontLeft.setSpeed(dockPlatformSpeed3 * Math.signum(gyro.getRoll()));
            backRight.setSpeed(dockPlatformSpeed3 * Math.signum(gyro.getRoll()));
            backLeft.setSpeed(dockPlatformSpeed3 * Math.signum(gyro.getRoll()));

        } else if (Math.abs(gyro.getRoll()) < 3) {
            frontRight.setSpeed(0);
        frontLeft.setSpeed(0);
        backRight.setSpeed(0);
        backLeft.setSpeed(0);
        }
    }
    //might not use
    public void moveDistance(double speed, double angle, double ramprate, double time) {
        Timer moveDistanceTimer = new Timer();
        moveDistanceTimer.reset();
        moveDistanceTimer.start();

        double direction;
        double maxSpeed;
        if (speed < 0) {
            maxSpeed = speed + 0.1;
            direction = -0.1;

        } else {
            maxSpeed = speed - 0.1;
            direction = 0.1;
        }
        if (moveDistanceTimer.get() < time) {
        frontRight.setSpeed(((maxSpeed/(1+Math.exp(-(ramprate*moveDistanceTimer.get()) + 4)))+direction));
        frontLeft.setSpeed(((maxSpeed/(1+Math.exp(-(ramprate*moveDistanceTimer.get()) + 4)))+direction));
        backRight.setSpeed(((maxSpeed/(1+Math.exp(-(ramprate*moveDistanceTimer.get()) + 4)))+direction));
        backLeft.setSpeed(((maxSpeed/(1+Math.exp(-(ramprate*moveDistanceTimer.get()) + 4)))+direction));
        } else {
            frontRight.setSpeed(0);
            frontLeft.setSpeed(0);
            backRight.setSpeed(0);
            backLeft.setSpeed(0);

        }
    }
    
    // Zero the Gryo
    public void resetGyro() {
        gyro.reset();
    }

    // Get the Gyro Angle (-180 to 180)
    public double gyroAngle() {
        return gyro.getYaw();
    }

    // Set the controller to be field oriented drive
    public void setFOD(boolean value) {
        isFieldCentric = value;
    }

    // Get the current FOD mode
    public boolean getFOD() {
        return isFieldCentric;
    }

    //returns the instance
    public static SwerveWheelController getInstance() {
        if (instance == null) {
            instance = new SwerveWheelController();
            instance.setDefaultCommand(new TeleopDrive());
        }

        return instance;
    }

    public void stop(){
        frontLeft.setSpeed(0);
        frontRight.setSpeed(0);
        backRight.setSpeed(0);
        backLeft.setSpeed(0);
    }

}