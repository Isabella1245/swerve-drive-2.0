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

import com.kauailabs.navx.frc.AHRS;

public class SwerveWheelController extends SubsystemBase implements Constants  {
    
    private static SwerveWheelController instance = null;
    
    //stating the drive motors
    private SwerveWheelDrive frontRightDrive = null;
    private SwerveWheelDrive frontLeftDrive = null;
    private SwerveWheelDrive backRightDrive = null;
    private SwerveWheelDrive backLeftDrive = null;

    //swerve wheels
    private SwerveWheel frontRight = null;
    private SwerveWheel frontLeft = null;
    private SwerveWheel backRight = null;
    private SwerveWheel backLeft = null;

    private AHRS gyro = null;

    // Get distance between wheels
    private double r = Math.sqrt((L * L) + (W * W));

    private boolean isFieldCentric = true;
    private boolean gyroEnabled = false;

    private SwerveWheelController(){
    
        //the motor controls are all talon srx
        //drive motors
        //potential robot container content
        frontRightDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, FRDid, true);
        frontLeftDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, FLDid, true);
        backRightDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, BRDid, true);
        backLeftDrive = new SwerveWheelDrive(SwerveWheelDriveType.TalonSRX, BLDid, true);
        //potential robot container content
        frontRight = new SwerveWheel(frontRightDrive, FRTid, FRTencoderID, FRTencoderOffset, "Front Right");
        frontLeft = new SwerveWheel(frontLeftDrive, FLTid, FLTencoderID, FLTencoderOffset, "Front Left");
        backRight = new SwerveWheel(backRightDrive, BRTid, BRTencoderID, BRTencoderOffset, "Back Right");
        backLeft = new SwerveWheel(backLeftDrive, BLTid, BLTencoderID, BLTencoderOffset, "Back Left");
    
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
    }

         // x = strafe, y = speed, z = rotation 
    // Holonomic drive
    public void drive(double x, double y, double z, double gyroValue) {

        //calculate magnitude of joystick
        // Calculate magnitude of joystick

        y *= -1;
        double magnitude = Math.sqrt((Math.pow(x, 2)) + (Math.pow(y,2)));
        double frontLeftSpeed = 0;
        double frontRightSpeed = 0;
        double backRightSpeed = 0;
        double backLeftSpeed = 0;
   
        if (magnitude >= 0.15) {

            // I got this bit of code from the NavX website
            if (isFieldCentric == true && gyroEnabled == true) {
                // Convert gyro angle to radians
                double gyro = gyroValue * Math.PI / 180;

                //field orientation math
                double temp = x * Math.cos(gyro) + y * Math.sin(gyro); 
                y = -x * Math.sin(gyro) + y * Math.cos(gyro); 
                x = temp;
            }

            // -------------------------------------
            // Do the swerve wheel math for speed and angles
            // -------------------------------------
            double a = x - z * (L / r);
            double b = x + z * (L / r);
            double c = y - z * (W / r);
            double d = y + z * (W / r);

            frontLeftSpeed = Math.sqrt((b * b) + (c * c));
            frontRightSpeed = Math.sqrt((b * b) + (d * d));
            backRightSpeed = Math.sqrt((a * a) + (d * d));
            backLeftSpeed = Math.sqrt((a * a) + (c * c));

            double backRightAngle = Math.atan2(a, d) * 180 / Math.PI;
            double backLeftAngle = Math.atan2(a, c) * 180 / Math.PI;
            double frontRightAngle = Math.atan2(b, d) * 180 / Math.PI;
            double frontLeftAngle = Math.atan2(b, c) * 180 / Math.PI ;      

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

        SmartDashboard.putNumber("magnitude", magnitude);
        SmartDashboard.putNumber("fr angle", frontRight.getSetpoint());
        SmartDashboard.putNumber("fl angle", frontLeft.getSetpoint());
        SmartDashboard.putNumber("br angle", backRight.getSetpoint());
        SmartDashboard.putNumber("bl angle", backLeft.getSetpoint());
        SmartDashboard.putNumber("gyro", gyroValue);
        SmartDashboard.putNumber("fl speed", frontLeftSpeed);
        SmartDashboard.putNumber("fr speed", frontRightSpeed);
        SmartDashboard.putNumber("Bl speed", backLeftSpeed);
        SmartDashboard.putNumber("br speed", backRightSpeed);
        SmartDashboard.putNumber("measurement", frontRight.getMeasurement());
        SmartDashboard.putNumber("abs angle deg", frontRight.getAbsAngleDeg());
        SmartDashboard.putNumber("ticks", frontRight.getTicks());
        SmartDashboard.putNumber("ticks to angle", frontRight.ticksToAngle());


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