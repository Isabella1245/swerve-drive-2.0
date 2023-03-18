package frc.robot;

public interface Constants{
    
    //dimensions of robot in inches
    public final double L = 23.50;
    public final double W = 17.75;

    //PIDF variables 
    //the difference between PID and PIDF:
    //PID has proportional, integral, and derivative controller actions
    //PIDF has proportional, integral, and derivative with first-order filter on derivative term
    
    public final double kP = 0.06;//2.25 //500 //0.8
    public final double kI = 0.0; //0.05
    public final double kD = 0.0009;//50 //5
    public final double kF = 0.0;

    //ArmExtension PID
    public final double eP = 0.1;
    public final double eI = 0.002;
    public final double eD = 0.0009;
    //ArmWrist PID
    public final double wP = 0.1;
    public final double wI = 0.002;
    public final double wD = 0.0009;

    //quadrature encoder ticks per rotation
    public final int quadCountsPerRotation = 1658;
    public final int analogCountsPerRotation = 2703;
    public final int twoAnalogCountsPerRotation = 4096;
    
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
    public final int FRTencoderOffset = 0;
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
    public final int analogPotMax = 790;
    public final int analogPotMin = 315;

    //extension- recheck
    public final int extensionElevatorMax = 6330;
    public final int extensionElevatorMin = -1450;

    //wrist
    public final int wristMax = 1420;
    public final int wristMin = -100;


    //arm encoders (digital)
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
    public final double floorHeight = 420;
    public final double midHeight = 650;
    public final double clearHeight = 420;
    public final double topHeight = 740;

    public final double floorExtenstion = 3360;
    public final double midExtenstion = 710;
    public final double topExtenstion = 5470;

    public final double floorWrist = 540;
    public final double midWrist = 780;
    public final double topWrist = 840;

    public final double retractHeight = 420;
    public final double retractExtension = 3360;

    public final double substationHeight = 680;
    public final double substationExtension = 0;
    public final double substationWrist = 811;

    //recalculate threshold height, actuator values have changed
    public final double thresholdHeight = 600;

    //AUTON CONSTANTE

    //MOBILITY
        //speed: 40% speed: 13ft 3 sec and 80%: 9 ft/s
    public final double B1MobSpeed = -0.3; //Same as R3
    public final double B2MobSpeed = -0.8; 
    public final double B3MobSpeed = -0.45; //Same as R1
        
    public final double R1MobSpeed = -0.45; // Same as B3
    public final double R2MobSpeed = -0.3;
    public final double R3MobSpeed = -0.3; //Same as B1
        //angle
    public final double B1MobAngle = 0; //-3
    public final double B2MobAngle = -5;
    public final double B3MobAngle = -2; //-3

    public final double R1MobAngle = -2; //-3
    public final double R2MobAngle = 0;
    public final double R3MobAngle = 0; //-3
        //time
    public final double B1MobTime = 7.8;
    public final double B2MobTime = 6.5;
    public final double B3MobTime = 7.8;

    public final double R1MobTime = 7.8;
    public final double R2MobTime = 0;
    public final double R3MobTime = 5.8;
    

    //DOCKING
        //speed
     public final double B1DockSpeed1 = 0.4; //-90deg, pos power
     public final double B1DockSpeed2 = 0.8;

     public final double B1DockAngle1 = -90; 
     public final double B1DockAngle2 = 0;

     public final double B1DockTime1 = 9.3;
     public final double B1DockTime2 = 10.3;

     //public final double B2DockSpeed1 = -0.4;

     public final double B3DockSpeed1 = 0.4; 
     public final double B3DockSpeed2 = 0.8;

     public final double B3DockAngle1 = 90; 
     public final double B3DockAngle2 = 0;

     public final double B3DockTime1 = 9.3; 
     public final double B3DockTime2 = 10.3;



     public final double R3DockSpeed1 = -0.2; //R3MobSpeed
     public final double R3DockSpeed2 = -0.2;


     public final double R1DockSpeed1 = -0.4; //R1ModSpeed
     public final double R1DockSpeed2 = -.04;

     //public final double R2DockSpeed1 = -0.4; //R2MobSpeed

         //angle

     //public final double B2DockAngle1 = 0; //B2MobAngle

     public final double R1DockAngle1 = 0; //R1MobAngle
     public final double R1DockAngle2 = -90;

     public final double R2DockAngle1 = 0; //R2MobAngle

     public final double R3DockAngle1 = 0; //R3MobAngle
     public final double R3DockAngle2 = 90;

         //time

     public final double B2DockTime1 = 4; //B2MobTime

     public final double R1DockTime1 = 4; //R1MobTime
     public final double R1DockTime2 = 2;

     public final double R2DockTime1 = 4; //R2MobTime

     public final double R3DockTime1 = 2; //R3MobTime
     public final double R3DockTime2 = 2;

    /*//position 1 - blue

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
    */

    //balance constants
    public final double dockRampAngle1 = 34.55;
    public final double dockRampAngle2 = 10.55;
    public final double dockPlatformAngle1 = 15; 

    public final double dockRampSpeed = 0.3;
    public final double dockPlatformSpeed1 = 0.2;
    public final double dockPlatformSpeed2 = 0.15;
    public final double dockPlatformSpeed3 = 0.1;

}

//that is all that was included in this module
