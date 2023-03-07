package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.Auton1;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.ArmController;
import frc.robot.subsystems.SwerveWheel;
import frc.robot.subsystems.SwerveWheelController;
import frc.robot.subsystems.SwerveWheelDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

//i think we can use the wpilib import for joysticks instead of the vikings' import for controller
//import viking.Controller;

public class Robot extends TimedRobot {
  
  public static Joysticks LowerDriver = new Joysticks(1);
  public static XboxControllers UpperDriver = new XboxControllers(0);
  //public static Joystick driver = new Joystick(0);
	private static CommandScheduler scheduler = CommandScheduler.getInstance();
  Timer timer = new Timer();

  public final SendableChooser<String> autonChooser = new SendableChooser<String>();

  private TeleopDrive  teleDrive;
  private Auton1 auton;

  

  @Override
  public void robotInit() {
    SwerveWheelController.getInstance();
    ArmController.getInstance();
    CameraServer.startAutomaticCapture();
    //put the schedule instance on the smart dashboard when the robot initializes
    SmartDashboard.putData(CommandScheduler.getInstance());

    autonChooser.setDefaultOption("3 piece blue side", "B1");
    autonChooser.addOption("3 piece red side", "R1");
    autonChooser.addOption("1 piece and balance blue side", "B2");
    autonChooser.addOption("1 piece and balace red side", "R2");
  }

  @Override
  public void robotPeriodic() {
    scheduler.run();
  }

  @Override
  public void disabledInit() {
    System.out.println("Disabled");
  }

  @Override
  public void autonomousInit() {
    System.out.println("Autonomous");
  }

  @Override
  public void autonomousPeriodic() {
    scheduler.run();
  }

  @Override
  public void teleopInit() {
    System.out.println("Teleop");
    timer.reset();
    timer.start();

  }

  @Override
  public void teleopPeriodic() {
    if (timer.get() > 1)
    scheduler.run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    scheduler.run();
  }

}