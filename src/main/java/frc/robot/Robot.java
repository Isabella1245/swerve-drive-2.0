package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.SwerveWheelController;
//i think we can use the wpilib import for joysticks instead of the vikings' import for controller
//import viking.Controller;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  
  public static Joystick driver = new Joystick(0);
	private static CommandScheduler scheduler = CommandScheduler.getInstance();

  @Override
  public void robotInit() {
    SwerveWheelController.getInstance();
  }

  @Override
  public void robotPeriodic() {}

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

  }

  @Override
  public void teleopPeriodic() {
    scheduler.run();
  }

}
