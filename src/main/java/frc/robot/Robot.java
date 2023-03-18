package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.TeleopArmActuator;
import frc.robot.commands.TeleopArmExtension;
import frc.robot.commands.TeleopArmWrist;
import frc.robot.commands.ArmActuatorMove;
import frc.robot.commands.ArmExtensionMove;
import frc.robot.commands.ArmWristMove;
import frc.robot.commands.BalanceRobot;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.DriveSegment;
import frc.robot.commands.DriveSegmentWithTime;
import frc.robot.commands.TeleopDrive;
import frc.robot.commands.TeleopClaw;
import frc.robot.subsystems.ArmActuator;
import frc.robot.subsystems.ArmExtension;
import frc.robot.subsystems.ArmWrist;
import frc.robot.subsystems.SwerveWheel;
import frc.robot.subsystems.SwerveWheelController;
import frc.robot.subsystems.SwerveWheelDrive;
import frc.robot.subsystems.PneumaticClaw;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot implements Constants{
  public static XboxControllers LowerDriver = new XboxControllers(1);
  //public static XboxControllers UpperDriver = new XboxControllers(0);
  private XboxControllers upperDriver;

	private static CommandScheduler scheduler;
  Timer timer = new Timer();

  private SwerveWheelController swerve;
  private ArmActuator armActuator;
  private ArmExtension armExtension;
  private ArmWrist armWrist;
  private PneumaticClaw claw;

  public final SendableChooser<String> autonChooser = new SendableChooser<>();;
  public final SendableChooser<String> dockChooser = new SendableChooser<>();;

  private String autonSelection, dockSelection;

  private static final String kScoreMobilityB1 = "B1: Score + Mobility";
  private static final String kScoreMobilityB2 = "B2: Score + Mobility";
  private static final String kScoreMobilityB3 = "B3: Score + Mobility";

  private static final String kScoreMobilityR1 = "R1: Score + Mobility";
  private static final String kScoreMobilityR2 = "R2: Score + Mobility";
  private static final String kScoreMobilityR3 = "R3: Score + Mobility";

  private static final String kDockYes = "Dock - Yes";
  private static final String kDockNo = "Dock - No";

  @Override
  public void robotInit() {
    swerve = SwerveWheelController.getInstance();
    //arm = ArmController.getInstance();
    
    upperDriver = new XboxControllers(0);

    armActuator = new ArmActuator();
    armExtension = new ArmExtension();
    armWrist = new ArmWrist();
    claw = new PneumaticClaw();

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
    
    
    Command scoreTop, mobility, sequence;
    scheduler.cancelAll();
    autonSelection = autonChooser.getSelected();
    dockSelection = dockChooser.getSelected();

    String manualAutonSelection = "test";

    scoreTop = new ArmActuatorMove(armActuator, topHeight);
    scoreTop = scoreTop.andThen(new ArmExtensionMove(armExtension,topExtenstion));
    scoreTop = scoreTop.alongWith(new ArmWristMove(armWrist,topWrist));
    scoreTop = scoreTop.andThen(new OpenClaw(claw));

    mobility = new DriveSegment(swerve, B1MobSpeed, B1MobAngle).withTimeout(5.3);
    mobility = mobility.andThen(new ArmExtensionMove(armExtension, floorExtenstion));
     
    //SCORE + MOBILITY
    /*B1: Score + Mobility*/
    if (manualAutonSelection.equals(kScoreMobilityB1)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB1);
      sequence = scoreTop;
      sequence = sequence.andThen(mobility);
      
     //B2: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityB2)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB2);
      sequence = scoreTop;

    //B3: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityB3)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityB3);
      sequence = scoreTop;
      sequence = sequence.andThen(mobility);

    //R1: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityR1)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR1);
      sequence = scoreTop;
      sequence = sequence.andThen(mobility);

      //R2: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityR2)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR2);
      sequence = scoreTop;
      
      //R3: Score + Mobility
    } else if (manualAutonSelection.equals(kScoreMobilityR3)) {
      SmartDashboard.putString("Auton Program:", kScoreMobilityR3);
      sequence = scoreTop;
      sequence = sequence.andThen(mobility);

    }     
    else {
      sequence = new OpenClaw(claw);
    }
    scheduler.schedule(sequence);
  }

  @Override
  public void autonomousPeriodic() {
   scheduler.run();
   System.out.println(autonSelection);
   System.out.println(dockSelection);
  }

  @Override
  public void teleopInit() {
    Command seqArmBot, seqArmMid, seqArmTop, seqArmLoad;
    System.out.println("Teleop");
    scheduler.cancelAll();

    upperDriver = new XboxControllers(0);

    armActuator.setDefaultCommand(new TeleopArmActuator(armActuator, upperDriver));
    armExtension.setDefaultCommand(new TeleopArmExtension(armExtension, upperDriver));
    armWrist.setDefaultCommand(new TeleopArmWrist(armWrist, upperDriver));
    claw.setDefaultCommand(new TeleopClaw(claw, upperDriver));
//save
    if (armActuator.getPot() > floorHeight) {
      seqArmBot = new ArmExtensionMove(armExtension,floorExtenstion).andThen(new ArmActuatorMove(armActuator, floorHeight)).alongWith(new ArmWristMove(armWrist,floorWrist));
    } else {
    seqArmBot = new ArmActuatorMove(armActuator, floorHeight);
    seqArmBot = seqArmBot.andThen(new ArmExtensionMove(armExtension,floorExtenstion));
    seqArmBot = seqArmBot.alongWith(new ArmWristMove(armWrist,floorWrist));
    upperDriver.aButton().whileTrue(seqArmBot);
    }

    if (armActuator.getPot() > floorHeight) {
      seqArmMid = new ArmActuatorMove(armActuator, midHeight).alongWith(new ArmExtensionMove(armExtension, midExtenstion)).alongWith(new ArmWristMove(armWrist, midWrist));
    } else {
    seqArmMid = new ArmActuatorMove(armActuator, midHeight).andThen(new ArmExtensionMove(armExtension,midExtenstion));
    //seqArmMid = seqArmMid.andThenArmExtensionMove(armExtension,midExtenstion);
    seqArmMid = seqArmMid.alongWith(new ArmWristMove(armWrist,midWrist));
    upperDriver.bButton().whileTrue(seqArmMid);
    }

    seqArmTop = new ArmActuatorMove(armActuator, topHeight);
    seqArmTop = seqArmTop.andThen(new ArmExtensionMove(armExtension,topExtenstion));
    seqArmTop = seqArmTop.alongWith(new ArmWristMove(armWrist,topWrist));
    upperDriver.yButton().whileTrue(seqArmTop);

    seqArmLoad = new ArmActuatorMove(armActuator, substationHeight);
    seqArmLoad = seqArmLoad.andThen(new ArmExtensionMove(armExtension,substationExtension));
    seqArmLoad = seqArmLoad.alongWith(new ArmWristMove(armWrist,substationWrist));
    upperDriver.xButton().whileTrue(seqArmLoad);
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

}