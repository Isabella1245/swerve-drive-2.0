package frc.robot;

public interface Constants{
    
    //dimensions of robot in inches
    public final double L = 12;
    public final double W = 12;

    //PIDF variables 
    //the difference between PID and PIDF:
    //PID has proportional, integral, and derivative controller actions
    //PIDF has proportional, integral, and derivative with first-order filter on derivative term
    public final double kP = 0.02;
    public final double kI = 0.0;
    public final double kD = 0.0;
    public final double kF = 0.0;

    //quadrature encoder ticks per rotation
    public final int quadCountsPerRotation = 1000;

    //Turn motor CAN ID
    // *** im not quite sure what the IDs are for ***
    public final int FLTid = 1;
    public final int FRTid = 2;
    public final int BRTid = 3;
    public final int BLTid = 4;

    //IDs for drive motors
    public final int FRDid = 5;
    public final int FLDid = 6;
    public final int BRDid = 7;
    public final int BLDid = 8;

    // Analog Encoder ID (i think they are refering to turning encoders)
    public final int FRTencoderID = 9;
    public final int FLTencoderID = 10;
    public final int BRTencoderID = 11;
    public final int BLTencoderID = 12;

    // Offset of encoders to make them face forwards
    public final int FRTencoderOffset = 50;
    public final int FLTencoderOffset = 90;
    public final int BRTencoderOffset = 198;
    public final int BLTencoderOffset = 15;

}

//that is all that was included in this module
