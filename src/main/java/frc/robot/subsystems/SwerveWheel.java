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
    
    private TalonSRX steerMotor;
    private AnalogInput absoluteEncoder;

    private int countsWhenFrwd;

    private SwerveWheelDrive drive;

    public SwerveWheel(SwerveWheelDrive drive, int m_steer, int analogEnc, int zeroOffset, String name) 
{
        super(new PIDController(kP, kI, kD));

        this.name = name;

        this.drive = drive;

        countsWhenFrwd = zeroOffset;

        steerMotor = new TalonSRX(m_steer);
        absoluteEncoder = new AnalogInput(analogEnc);

        //reset all settings when startup
        steerMotor.configFactoryDefault();

        // Set the feedback device for the steering (turning) Talon SRX
		steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		// Set the current quadrature position relative to the analog position to make sure motor
		// has 0 position on startup
		steerMotor.setSelectedSensorPosition(getAbsAngleDeg() * quadCountsPerRotation / 180);

        // sets the current quadrature position relative to the analog position to make sure the motor has 0 position on startup
        //sets the input range of the PIDF so that it will only accept angles between -180 and 180
        // or in our case, -90 to 90
        getController().enableContinuousInput(-180, 180);

        //set name for viewing in smart dashboard
        this.setName(name);
    }

    //get the current angle of the analog encoder
    //not sure how the math works
    public int getAbsAngleDeg() {
        return (int)(180 * absoluteEncoder.getValue() - countsWhenFrwd) / 4096;
    }

    //get current ticks
    public int getTicks() {
        return (int)steerMotor.getSelectedSensorPosition();
    }
    
     public void setSpeed(double speed) {
		drive.setSpeed(speed);
	}

    //convert ticks to angle bound from -180 to 180
    public double ticksToAngle(int ticks) {
        double angleTicks = ticks % quadCountsPerRotation;

        double result = (angleTicks / (quadCountsPerRotation / 2)) * 180;

        //make sure the ticks is alsways within the limit
        if (result > 180) {
            result -= 360;
        }

        return result;
    }

    @Override
    protected double getMeasurement() {
        return ticksToAngle(getTicks());
    }

    //not sure what this class does
    @Override
    protected void useOutput(double output, double setpoint) {
		steerMotor.set(ControlMode.PercentOutput, output);
	}
    
}