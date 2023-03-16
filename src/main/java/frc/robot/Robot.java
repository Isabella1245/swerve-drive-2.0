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
import frc.robot.commands.DriveSegmentWithTime;
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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

//i think we can use the wpilib import for joysticks instead of the vikings' import for controller
//import viking.Controller;

public class Robot extends TimedRobot implements Constants{
  
  public static XboxControllers LowerDriver = new XboxControllers(1);
  public static XboxControllers UpperDriver = new XboxControllers(0);
  //public static Joystick driver = new Joystick(0);
	private static CommandScheduler scheduler;
  Timer timer = new Timer();

  private SwerveWheelController swerve;
  private ArmController arm;

  public final SendableChooser<String> autonChooser = new SendableChooser<>();;
  public final SendableChooser<String> dockChooser = new SendableChooser<>();;

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
    //autonChooser.addOption("B1: Score + Mobility + Dock", kScoreMobilityDockB1);
    //autonChooser.addOption("B2: Score + Mobility + Dock", kScoreMobilityDockB2);
    //autonChooser.addOption("B3: Score + Mobility + Dock", kScoreMobilityDockB3);

    //autonChooser.addOption("R1: Score + Mobility + Dock", kScoreMobilityDockR1);
    //autonChooser.addOption("R2: Score + Mobility + Dock", kScoreMobilityDockR2);
    //autonChooser.addOption("R3: Score + Mobility + Dock", kScoreMobilityDockR3);

    SmartDashboard.putData(autonChooser);
    SmartDashboard.putData(dockChooser);
    System.out.println((autonSelection));
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
    timer.reset();
    timer.start();
    
    

    Command sequence1, sequence2;
    scheduler.cancelAll();
    autonSelection = autonChooser.getSelected();
    dockSelection = dockChooser.getSelected();


    String manualAutonSelection = kScoreMobilityR2;


     
    //SCORE + MOBILITY
    /*B1: Score + Mobility*/
    if (manualAutonSelection.equals(kScoreMobilityB1)) {
      //SmartDashboard.putString("Auton Program:", kScoreMobilityB1);
      //save
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet).withTimeout(3.8);
      sequence1 = sequence1.andThen(new OpenClaw(arm)).withTimeout(4.3);
      sequence1 = sequence1.andThen(new DriveSegment(swerve, B1MobSpeed, B1MobAngle)).withTimeout(5.3);
      sequence1 = sequence1.andThen(new SetExtensionPos(arm, 0)).withTimeout(7.3);
      

      //sequence1 = sequence1.andThen(new DriveSegment(swerve, B1DockSpeed1, B1DockAngle1)).withTimeout(B1DockTime1);
      //sequence1 = sequence1.andThen(new DriveSegment(swerve, B1DockSpeed2, B1DockAngle2)).withTimeout(B1DockTime2);
      
      
      /*if (dockSelection.equals(kDockYes)) {
        sequence1 = new DriveSegment(swerve, B1DockSpeed1, B1DockAngle1)).withTimeout(B1DockTime1);
        sequence1 = sequence1.andThen(new DriveSegment(swerve, B1DockSpeed2, B1DockAngle2)).withTimeout(B1DockTime2);
        sequence1 = sequence1.andThen(new BalanceRobot(swerve));
      }
      */

     //B2: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityB2)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB2);
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet).withTimeout(3.8);
      sequence1 = sequence1.andThen(new OpenClaw(arm)).withTimeout(4.3);
      sequence1 = sequence1.andThen(new SetExtensionPos(arm, 0)).withTimeout(6.3);

      //sequence1 = sequence1.andThen(new DriveSegment(swerve, B2MobSpeed, B2MobAngle)).withTimeout(B2MobTime);
      //sequence1 = sequence1.andThen(new BalanceRobot(swerve));
      
      
      //save
    //B3: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityB3)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB3);

      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet).withTimeout(3.8);
      sequence1 = sequence1.andThen(new OpenClaw(arm)).withTimeout(4.3);
      sequence1 = sequence1.andThen(new DriveSegment(swerve, B3MobSpeed, B3MobAngle)).withTimeout(5.3);
      sequence1 = sequence1.andThen(new SetExtensionPos(arm, 0)).withTimeout(7.3);

      //sequence1 = sequence1.andThen(new DriveSegment(swerve, B3DockSpeed1, B3DockAngle1)).withTimeout(B3DockTime1);
      //sequence1 = sequence1.andThen(new DriveSegment(swerve, B3DockSpeed2, B3DockAngle2)).withTimeout(B3DockTime2);
      
    
      //R1: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityR1)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR1);

      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet).withTimeout(3.8);
      sequence1 = sequence1.andThen(new OpenClaw(arm)).withTimeout(4.3);
      sequence1 = sequence1.andThen(new DriveSegment(swerve, R1MobSpeed, R1MobAngle)).withTimeout(5.3);
      sequence1 = sequence1.andThen(new SetExtensionPos(arm, 0)).withTimeout(7.3);


      //R2: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityR2)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR2);

      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet).withTimeout(3.8);
      sequence1 = sequence1.andThen(new OpenClaw(arm)).withTimeout(4.3);
      sequence1 = sequence1.andThen(new SetExtensionPos(arm, 0)).withTimeout(6.3);

      //sequence1 = sequence1.andThen(new DriveSegment(swerve, R2MobSpeed, R2MobAngle)).withTimeout(R2MobTime);
      //sequence1 = sequence1.andThen(new BalanceRobot(swerve));
      
      //R3: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityR3)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR3);

      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet).withTimeout(3.8);
      sequence1 = sequence1.andThen(new OpenClaw(arm)).withTimeout(4.3);
      sequence1 = sequence1.andThen(new DriveSegment(swerve, R3MobSpeed, R3MobAngle)).withTimeout(5.3);
      sequence1 = sequence1.andThen(new SetExtensionPos(arm, 0)).withTimeout(7.3);
//save
    }
     /* //SCORE + MOBILITY + DOCK
    //B1
    else if (autonSelection.equals(kScoreMobilityDockB1)){
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence1 = sequence1.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence1 = sequence1.andThen(new OpenClaw(arm));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, B1DockSpeed1, B1DockAngle1));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, B1DockSpeed2, B1DockAngle2));
    //B2
    } else if (autonSelection.equals(kScoreMobilityDockB2)){
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence1 = sequence1.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence1 = sequence1.andThen(new OpenClaw(arm));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, B2DockSpeed1, B2DockAngle1));
    //B3
    } else if (autonSelection.equals(kScoreMobilityDockB3)){
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence1 = sequence1.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence1 = sequence1.andThen(new OpenClaw(arm));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, B3DockSpeed1, B3DockAngle1));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, B3DockSpeed2, B3DockAngle2));
    //R1
    } else if (autonSelection.equals(kScoreMobilityDockR1)){
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence1 = sequence1.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence1 = sequence1.andThen(new OpenClaw(arm));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, R1DockSpeed1, R1DockAngle1));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, R1DockSpeed2, R1DockAngle2));
    //R2
    } else if (autonSelection.equals(kScoreMobilityDockR2)){
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence1 = sequence1.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence1 = sequence1.andThen(new OpenClaw(arm));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, R2DockSpeed1, R2DockAngle1));
    //R3
    } else if (autonSelection.equals(kScoreMobilityDockR1)){
      sequence1 = new SetGamePiece(arm, topHeight1, topExtenstion, wristSet);
      sequence1 = sequence1.andThen(new SetGamePiece(arm, topHeight2, topExtenstion, wristSet));
      sequence1 = sequence1.andThen(new OpenClaw(arm));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, R3DockSpeed1, R3DockAngle1));
      sequence1 = sequence1.andThen(new DriveSegment(swerve, R3DockSpeed2, R3DockAngle2));

    */  else {
      sequence1 = new OpenClaw(arm);
      sequence2 = new OpenClaw(arm);
    }
    scheduler.schedule(sequence1);
    //scheduler.schedule(sequence2);
  }

  @Override
  public void autonomousPeriodic() {
   scheduler.run();
   System.out.println(autonSelection);
   System.out.println(dockSelection);
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