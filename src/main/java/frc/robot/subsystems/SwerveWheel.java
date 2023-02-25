package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SwerveWheel extends PIDSubsystem implements Constants {

    public String name;

    public double PIDoutput;
    
    private TalonSRX steerMotor;

    private MotorController controller;

    private AnalogInput absoluteEncoder;

    private int countsWhenFrwd;

    private SwerveWheelDrive drive;

    private boolean reversePhase;

    //double initialPoint;

    public SwerveWheel(SwerveWheelDrive drive, int m_steer, int analogEnc, int zeroOffset, String name, boolean isReversePhase) 
{
        super(new PIDController(kP, kI, kD));

        this.name = name;

        this.drive = drive;

        countsWhenFrwd = zeroOffset;

        steerMotor = new TalonSRX(m_steer);
        steerMotor.setSensorPhase(isReversePhase);

        absoluteEncoder = new AnalogInput(analogEnc);

        //reset all settings when startup
        steerMotor.configFactoryDefault();

        // Set the feedback device for the steering (turning) Talon SRX
		steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		// Set the current quadrature position relative to the analog position to make sure motor
		// has 0 position on startup

        //we reviewed this and it is correct :)
		steerMotor.setSelectedSensorPosition((getAbsAngleDeg() * quadCountsPerRotation) / 360);

        // sets the current quadrature position relative to the analog position to make sure the motor has 0 position on startup
        //sets the input range of the PIDF so that it will only accept angles between -180 and 180
        getController().enableContinuousInput(-180, 180);
        getController().setTolerance(5);

        //set name for viewing in smart dashboard
        this.setName(name);

        //initialPoint = steerMotor.getSelectedSensorPosition();
    }

    //get the current angle of the analog encoder
    /*
    this math is similar to the one we created when converting the pg encoders to degrees, we found
    the values it is reading at 0, 90, 180, 270, and 360 degrees. then we used google sheets to
    create a linear relation between them, this linear equation is wrong (obviously) and it may be
    whats messing up our math
    */
     //we reviewed this and it is correct :)
    public int getAbsAngleDeg() {
        return (int)((absoluteEncoder.getValue() - countsWhenFrwd) * 360 / analogCountsPerRotation);
    }

    //get current ticks
    public int getTicks() {
        return (int) steerMotor.getSelectedSensorPosition();
    }
    
     public void setSpeed(double speed) {
		drive.setSpeed(speed);
	}

    //convert ticks to angle bound from -180 to 180
    public double ticksToAngle(int ticks) {
        double angleTicks = ticks % quadCountsPerRotation; //820

        double result = (angleTicks / (quadCountsPerRotation)) * 360; //820/1640*360 = 180

        //make sure the ticks is alsways within the limit
        if (result > 180) { //180
            result -= 360; //360
        }

        return result;
    }


    @Override
    protected double getMeasurement() {
        return ticksToAngle(getTicks());
    }
    

    public double getTurnMotorSpeed() {
        return steerMotor.getMotorOutputPercent();
    }

    public int getAbsEncValue() {
        return (int)absoluteEncoder.getValue();
    }

    public int getQuadEncZeroSetpoint() {
        return (int)(getAbsAngleDeg() * quadCountsPerRotation / 180);
    }

    //not sure what this class does
    
    
    @Override
    protected void useOutput(double output, double setpoint) {
		steerMotor.set(ControlMode.PercentOutput, output);
        //steerMotor.set(ControlMode.PercentOutput, output);
        //where does output come from??????????????
        PIDoutput = output;

	}

    public double getOutput(){
        return PIDoutput;
    }
    
    

    public int getPValues() {
        int coefficientP = (int)getController().getP();
        return (int) (coefficientP);
    }

    public int getIValues() {
        int coefficientI = (int)getController().getI();
        return (int) (coefficientI);
    }

    public int getDValues() {
        int coefficientD = (int)getController().getD();
        return (int) (coefficientD);
    }

    public double getTolerance() {
        double tolerance = (double)getController().getPositionTolerance();
        return (double) (tolerance);
    }

    public double getError() {
        double error = (double)getController().getPositionError();
        return (double) (error);
    }

    public double getCalculation() {
        double calculation = (double)getController().calculate(getMeasurement());
        return (double) (calculation);
    }


    /*
    public double getInitialPgValue() {
        return (double) (initialPoint);
    }
    */
    
}