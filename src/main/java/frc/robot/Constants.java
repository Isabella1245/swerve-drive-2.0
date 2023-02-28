package frc.robot;

public interface Constants{
    
    //dimensions of robot in inches
    public final double L = 33.125;
    public final double W = 18.5;

    //PIDF variables 
    //the difference between PID and PIDF:
    //PID has proportional, integral, and derivative controller actions
    //PIDF has proportional, integral, and derivative with first-order filter on derivative term
    
    public final double kP = 0.05;//2.25 //500
    public final double kI = 0.0; //0.05
    public final double kD = 0.0009;//50 //5
    public final double kF = 0.0;

    //arm pid
    public final double P = 0.0;
    public final double I = 0.0;
    public final double D = 0.0;
    public final double F = 0.0;

    //quadrature encoder ticks per rotation
    public final int quadCountsPerRotation = 1640;
    public final int analogCountsPerRotation = 2700;
    
    public final int armQuadCountsPerRotation = 8192;
    //Turn motor CAN ID
    // *** im not quite sure what the IDs are for ***
    public final int FLTid = 1; //2
    public final int FRTid = 2; //3
    public final int BRTid = 3; //5
    public final int BLTid = 5; //7

    //IDs for drive motors
    public final int FRDid = 6; //4
    public final int FLDid = 1; //1
    public final int BRDid = 1; //6
    public final int BLDid = 1; //8

    // Analog Encoder ID (i think they are refering to turning encoders)
    public final int FRTencoderID = 2; //2
    public final int FLTencoderID = 3; //3
    public final int BRTencoderID = 0; //0
    public final int BLTencoderID = 4; //1

    // Offset of encoders to make them face forwards
    public final int FRTencoderOffset = 0;
    public final int FLTencoderOffset = 171;
    public final int BRTencoderOffset = 0;
    public final int BLTencoderOffset = 0;

    //ARM STUFF
    //IDs for arm motors
    public final int actuatorMotorID = 8;
    public final int extensionMotorID = 7;
    public final int clawTwistMotorID = 4;

    //analog potentiometer
    public final int analogPotID = 1;
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
    public final double midHeight = 2900;
    public final double topHeight = 2900; //2900

    public final double floorExtenstion = 7000;
    public final double midExtenstion = 7000;
    public final double topExtenstion = 7000; //7000

    public final double retractHeight = 1000;
    public final double retractExtension = -100;

    public final double potThreshold = 1000;
    public final double retractThreshold = 1000;

}

//that is all that was included in this module
