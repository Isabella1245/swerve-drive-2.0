package frc.robot;

public interface Constants{
    
    //dimensions of robot in inches
    public final double L = 23.50;
    public final double W = 17.75;

    //PIDF variables 
    //the difference between PID and PIDF:
    //PID has proportional, integral, and derivative controller actions
    //PIDF has proportional, integral, and derivative with first-order filter on derivative term
    
    public final double kP = 0.05;//2.25 //500
    public final double kI = 0.0; //0.05
    public final double kD = 0.0009;//50 //5
    public final double kF = 0.0;

    //arm pid
    public final double P = 0.2;
    public final double I = 0.0;
    public final double D = 0.0009;
    public final double F = 0.0;

    //quadrature encoder ticks per rotation
    public final int quadCountsPerRotation = 1640;
    public final int analogCountsPerRotation = 2700;
    
    public final int armQuadCountsPerRotation = 8192;
    //Turn motor CAN ID
    public final int FLTid = 4; //2
    public final int FRTid = 11; //3
    public final int BRTid = 10; //5
    public final int BLTid = 5; //7

    //IDs for drive motors
    public final int FRDid = 14; //4
    public final int FLDid = 1; //1
    public final int BRDid = 13; //6
    public final int BLDid = 2; //8

    //drive ecoder IDs
    public final int FRDencoderID = 5; //2

    // Analog Encoder ID (i think they are refering to turning encoders)
    public final int FRTencoderID = 2; //2
    public final int FLTencoderID = 3; //3
    public final int BRTencoderID = 0; //0
    public final int BLTencoderID = 1; //1

    // Offset of encoders to make them face forwards
    public final int FRTencoderOffset = 0; //171?
    public final int FLTencoderOffset = 0; //171?
    public final int BRTencoderOffset = 0;
    public final int BLTencoderOffset = 0;

    //ARM STUFF
    //IDs for arm motors
    public final int actuatorMotorID = 3;
    public final int extensionMotorID = 9;
    public final int clawTwistMotorID = 6;

    //analog potentiometer
    public final int analogPotID = 4;
    public final int analogPotMax = 3600;
    public final int analogPotMin = -100;
    //arm encoders
    public final int extensionEncoderA = 0;
    public final int extensionEncoderB = 1;
    public final int clawEncoderA = 2;
    public final int clawEncoderB = 3;

    //PNEUMATICS
    public final int compressorModule = 0;
    public final int solenoidChannel = 0;
    public final int counterChannel = 5;
    //tbd

    //arm auton constants
    //angles in potentiometer values
    public final double floorHeight = 2900;
    public final double midHeight = 2420;
    public final double topHeight = 3000; //2900

    public final double floorExtenstion = 7000;
    public final double midExtenstion = 0;
    public final double topExtenstion = 5185; //7000

    public final double retractHeight = 1000;
    public final double retractExtension = -100;

    public final double potThreshold = 1000;
    public final double retractThreshold = 1000;

    //drive Auton Constants

    //position 1 - blue
    public final double p1BlueSpeed = 1;
    //public final double p1BlueRamprate

    //public final double blueDrive1a = -337.3;
    public final double blueDriveAngle1a = 0;
    public final double blueTime1a = 0;

    //public final double blueDrive1b = 173.93;
    public final double blueDriveAngle1b = 41.83;
    public final double blueTime1b = 0;

    //public final double blueDrive2a = -207.93;
    public final double blueDriveAngle2a = 42.52;
    public final double blueTime2a = 0;

    //public final double blueDrive2b = 337.3;
    public final double blueDriveAngle2b = 0;
    public final double blueTime2b = 0;

    //public final double blueDrive3a = -467.27;
    public final double blueDriveAngle3a = 4.97;
    public final double blueTime3a = 0;

    //public final double blueDrive4a = -375.52;
    public final double blueDriveAngle4a = 9.1;   
    public final double blueTime4a = 0;

    //public final double blueDrive4b = 155.52;
    public final double blueDriveAngle4b = -51.79;
    public final double blueTime4b = 0;

    //postion 1 - red
    //public final double redDrive1a = -337.3;
    public final double redDriveAngle1a = 0;
    public final double redTime1a = 0;

    //public final double redDrive1b = 173.93;
    public final double redDriveAngle1b = -41.83;
    public final double redTime1b = 0;

    //public final double redDrive2a = -207.93;
    public final double redDriveAngle2a = -42.52;
    public final double redTime2a = 0;

    //public final double redDrive2b = 337.3;
    public final double redDriveAngle2b = 0;
    public final double redTime2b = 0;

    //public final double redDrive3a = -467.27;
    public final double redDriveAngle3a = -4.97;
    public final double redTime3a = 0;

    //public final double redDrive4a = -375.52;
    public final double redDriveAngle4a = -9.1;
    public final double redTime4a = 0;

    //public final double redDrive4b = 155.52;
    public final double redDriveAngle4b = 51.79;
    public final double redTime4b = 0;


    //position 2 blue

    public final double p2blueSpeed1 = -0.7;
    public final double p2blueDriveAngle1 = 0;
    public final double p2blueRamprate1 = 7;

    public final double p2blueDrive2 = -5;
    public final double p2blueDriveAngle2 = 0;

    //turn robot
    public final double turn180 = 180;
    public final double turn0 = 0;

    public final double frSpinAngle = -52.4;
    public final double flSpinAngle = 52.4;

}

//that is all that was included in this module
