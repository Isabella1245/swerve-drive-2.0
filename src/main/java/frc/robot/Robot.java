package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.SetActuatorArmPos;
import frc.robot.commands.SetArmRotationPos;
import frc.robot.commands.SetExtensionPos;
import frc.robot.commands.BalanceRobot;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.DriveSegment;
import frc.robot.commands.SetGamePiece;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.ArmController;
import frc.robot.subsystems.ArmPart;
import frc.robot.subsystems.SwerveWheel;
import frc.robot.subsystems.SwerveWheelController;
import frc.robot.subsystems.SwerveWheelDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

//i think we can use the wpilib import for joysticks instead of the vikings' import for controller
//import viking.Controller;

public class Robot extends TimedRobot implements Constants{
  
  public static XboxControllers LowerDriver = new XboxControllers(2);
  public static XboxControllers UpperDriver = new XboxControllers(0);
  //public static Joystick driver = new Joystick(0);
	private static CommandScheduler scheduler;
  Timer timer = new Timer();

  private SwerveWheelController swerve;
  private ArmController arm;

  public final SendableChooser<String> autonChooser = new SendableChooser<String>();
  public final SendableChooser<String> dockChooser = new SendableChooser<String>();

  private TeleopDrive  teleDrive;

  private String autonSelection, dockSelection;

  private static final String kScoreMobilityB1 = "B1: Score + Mobility";
  private static final String kScoreMobilityB2 = "B2: Score + Mobility";
  private static final String kScoreMobilityB3 = "B3: Score + Mobility";

  private static final String kScoreMobilityR1 = "R1: Score + Mobility";
  private static final String kScoreMobilityR2 = "R2: Score + Mobility";
  private static final String kScoreMobilityR3 = "R3: Score + Mobility";

  private static final String kDockYes = "Dock - Yes";
  private static final String kDockNo = "Dock - No";

  private static final String kScoreMobilityDockB1 = "B1: Score + Mobility + Dock";
  private static final String kScoreMobilityDockB2 = "B2: Score + Mobility + Dock";
  private static final String kScoreMobilityDockB3 = "B3: Score + Mobility + Dock";

  private static final String kScoreMobilityDockR1 = "R1: Score + Mobility + Dock";
  private static final String kScoreMobilityDockR2 = "R2: Score + Mobility + Dock";
  private static final String kScoreMobilityDockR3 = "R3: Score + Mobility + Dock";  

  @Override
  public void robotInit() {
    swerve = SwerveWheelController.getInstance();
    arm = ArmController.getInstance();
    scheduler = CommandScheduler.getInstance();

    CameraServer.startAutomaticCapture();
    //put the schedule instance on the smart dashboard when the robot initializes
    SmartDashboard.putData(CommandScheduler.getInstance());

    //score + mobility
    autonChooser.setDefaultOption("B1: Score + Mobility", kScoreMobilityB1);
    autonChooser.addOption("B2: Score + Mobility", kScoreMobilityB2);
    autonChooser.addOption("B3: Score + Mobility", kScoreMobilityB3);

    autonChooser.addOption("R1: Score + Mobility", kScoreMobilityR1);
    autonChooser.addOption("R2: Score + Mobility", kScoreMobilityR2);
    autonChooser.addOption("R3: Score + Mobility", kScoreMobilityR3);

    dockChooser.setDefaultOption("Dock - No", kDockNo);
    dockChooser.addOption("Dock - Yes", kDockYes);

    //score + mobility + dock
    autonChooser.addOption("B1: Score + Mobility + Dock", kScoreMobilityDockB1);
    autonChooser.addOption("B2: Score + Mobility + Dock", kScoreMobilityDockB2);
    autonChooser.addOption("B3: Score + Mobility + Dock", kScoreMobilityDockB3);

    autonChooser.addOption("R1: Score + Mobility + Dock", kScoreMobilityDockR1);
    autonChooser.addOption("R2: Score + Mobility + Dock", kScoreMobilityDockR2);
    autonChooser.addOption("R3: Score + Mobility + Dock", kScoreMobilityDockR3);

    SmartDashboard.putData(autonChooser);
    SmartDashboard.putData(dockChooser);
  }

  @Override
  public void robotPeriodic() {
    scheduler.run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void autonomousInit() {
    Command sequence;
    scheduler.cancelAll();
    autonSelection = autonChooser.getSelected();
    dockSelection = dockChooser.getSelected();
    //SCORE + MOBILITY
    /*B1: Score + Mobility*/
    if (autonSelection.equals(kScoreMobilityB1)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB1);
      //sequence = new SetActuatorArmPos(arm, thresholdHeight);
      //sequence = sequence.andThen(new SetActuatorArmPos(arm, topHeight1)).alongWith(new SetExtensionPos(arm, topExtenstion)).alongWith(new SetArmRotationPos(arm, wristSet));
      sequence = new SetActuatorArmPos(arm, topHeight1);
      sequence = sequence.andThen(new SetExtensionPos(arm, topExtenstion));
      sequence = sequence.andThen(new SetArmRotationPos(arm, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, B1MobSpeed, B1MobAngle).withTimeout(B1MobTime));
      if (dockSelection.equals(kDockYes)) {
        sequence = sequence.andThen(new DriveSegment(swerve, B1DockSpeed1, B1DockAngle1)).withTimeout(B1DockTime1);
        sequence = sequence.andThen(new BalanceRobot(swerve));
      }
      else {
        sequence = new CloseClaw(arm);
      }
      /*B2: Score + Mobility*/
    } else if (autonSelection.equals(kScoreMobilityB2)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB2);
      sequence = new SetActuatorArmPos(arm, topHeight1);
      sequence = sequence.andThen(new SetExtensionPos(arm, topExtenstion));
      sequence = sequence.andThen(new SetArmRotationPos(arm, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, B2MobSpeed, B2MobAngle).withTimeout(B2MobTime));
    /*B3: Score + Mobility*/
    } else if (autonSelection.equals(kScoreMobilityB3)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB3);
      sequence = new SetActuatorArmPos(arm, topHeight1);
      sequence = sequence.andThen(new SetExtensionPos(arm, topExtenstion));
      sequence = sequence.andThen(new SetArmRotationPos(arm, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, B3MobSpeed, B3MobAngle).withTimeout(B3MobTime));
      /*R1: Score + Mobility*/
    } else if (autonSelection.equals(kScoreMobilityR1)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR1);
      sequence = new SetActuatorArmPos(arm, topHeight1);
      sequence = sequence.andThen(new SetExtensionPos(arm, topExtenstion));
      sequence = sequence.andThen(new SetArmRotationPos(arm, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, R1MobSpeed, R1MobAngle).withTimeout(R1MobTime));
      /*R2: Score + Mobility*/
    } else if (autonSelection.equals(kScoreMobilityR2)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR2);
      sequence = new SetActuatorArmPos(arm, topHeight1);
      sequence = sequence.andThen(new SetExtensionPos(arm, topExtenstion));
      sequence = sequence.andThen(new SetArmRotationPos(arm, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, R2MobSpeed, R2MobAngle).withTimeout(R2MobTime));
      /*R3: Score + Mobility*/
    } else if (autonSelection.equals(kScoreMobilityR3)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR3);
      sequence = new SetActuatorArmPos(arm, topHeight1);
      sequence = sequence.andThen(new SetExtensionPos(arm, topExtenstion));
      sequence = sequence.andThen(new SetArmRotationPos(arm, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, R3MobSpeed, R3MobAngle).withTimeout(R3MobTime));
      
    }
      //SCORE + MOBILITY + DOCK
    /*B1*/
    else if (autonSelection.equals(kScoreMobilityDockB1)){
      sequence = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence = sequence.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, B1DockSpeed1, B1DockAngle1));
      sequence = sequence.andThen(new DriveSegment(swerve, B1DockSpeed2, B1DockAngle2));
    /*B2*/
    } else if (autonSelection.equals(kScoreMobilityDockB2)){
      sequence = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence = sequence.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, B2DockSpeed1, B2DockAngle1));
    /*B3*/
    } else if (autonSelection.equals(kScoreMobilityDockB3)){
      sequence = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence = sequence.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, B3DockSpeed1, B3DockAngle1));
      sequence = sequence.andThen(new DriveSegment(swerve, B3DockSpeed2, B3DockAngle2));
    /*R1*/
    } else if (autonSelection.equals(kScoreMobilityDockR1)){
      sequence = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence = sequence.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, R1DockSpeed1, R1DockAngle1));
      sequence = sequence.andThen(new DriveSegment(swerve, R1DockSpeed2, R1DockAngle2));
    /*R2*/
    } else if (autonSelection.equals(kScoreMobilityDockR2)){
      sequence = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence = sequence.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, R2DockSpeed1, R2DockAngle1));
    /*R3*/
    } else if (autonSelection.equals(kScoreMobilityDockR1)){
      sequence = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence = sequence.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence = sequence.andThen(new OpenClaw(arm));
      sequence = sequence.andThen(new DriveSegment(swerve, R3DockSpeed1, R3DockAngle1));
      sequence = sequence.andThen(new DriveSegment(swerve, R3DockSpeed2, R3DockAngle2));

    } else {
      sequence = new OpenClaw(arm);
    }
    scheduler.schedule(sequence);
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
    scheduler.cancelAll();
    scheduler.schedule(teleDrive);

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