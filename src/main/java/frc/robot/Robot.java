package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
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

  public final SendableChooser<Double> autonChooser = new SendableChooser<>();;
  public final SendableChooser<Double> dockChooser = new SendableChooser<>();;

  private double autonSelection, dockSelection;

  private static final double kScoreMobilityB1 = 1;
  private static final double kScoreMobilityB2 = 2;
  private static final double kScoreMobilityB3 = 3;

  private static final double kScoreMobilityR1 = 4;
  private static final double kScoreMobilityR2 = 5;
  private static final double kScoreMobilityR3 = 6;

  private static final double kDockYes = 1;
  private static final double kDockNo = 0;
  Command seqArmBot, seqArmMid, seqArmTop, seqArmLoad;
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
    if (autonChooser.getSelected() == (kScoreMobilityB1)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityB1);
      sequence = scoreTop;
      sequence = sequence.andThen(mobility);
      
     //B2: Score + Mobility
    } else if (autonSelection == (kScoreMobilityB2)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityB2);
      sequence = scoreTop;

    //B3: Score + Mobility
    } else if (autonSelection == (kScoreMobilityB3)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityB3);
      sequence = scoreTop;
      sequence = sequence.andThen(mobility);

    //R1: Score + Mobility
    } else if (autonSelection == (kScoreMobilityR1)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityR1);
      sequence = scoreTop;
      sequence = sequence.andThen(mobility);

      //R2: Score + Mobility
    } else if (autonSelection == (kScoreMobilityR2)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityR2);
      sequence = scoreTop;
      
      //R3: Score + Mobility
    } else if (autonSelection == (kScoreMobilityR3)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityR3);
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

    //FLOOR HEIGHT BUTTON
    //fixed for now- ready to test

    /*
    if (armActuator.getPot() > floorHeight) {
      //seqArmBot = new ArmExtensionMove(armExtension,floorExtenstion).alongWith(new ArmActuatorMove(armActuator, floorHeight).alongWith(new ArmWristMove(armWrist,floorWrist)));
      seqArmBot = Commands.parallel(new ArmExtensionMove(armExtension, floorExtenstion), new ArmActuatorMove(armActuator, floorHeight), new ArmWristMove(armWrist,floorWrist));
    } else {
    seqArmBot = new ArmActuatorMove(armActuator, floorHeight);
    seqArmBot = seqArmBot.andThen(new ArmExtensionMove(armExtension,floorExtenstion).alongWith(new ArmWristMove(armWrist,floorWrist)));
    }
    */
    //seqArmBot = Commands.parallel(new ArmExtensionMove(armExtension, floorExtenstion), new ArmActuatorMove(armActuator, floorHeight), new ArmWristMove(armWrist,floorWrist));
    //upperDriver.aButton().whileTrue(seqArmBot);

    //MID HEIGHT BUTTON
    //fixed for now- ready to test
    /*if (armActuator.getPot() > floorHeight) {
      seqArmMid = new ArmActuatorMove(armActuator, midHeight).alongWith(new ArmExtensionMove(armExtension, midExtenstion).alongWith(new ArmWristMove(armWrist, midWrist)));
      //seqArmMid = Commands.parallel(new ArmExtensionMove(armExtension, midExtenstion), new ArmActuatorMove(armActuator, midHeight), new ArmWristMove(armWrist,midWrist));
    } else {
    seqArmMid = new ArmActuatorMove(armActuator, midHeight);
    seqArmMid = seqArmMid.andThen(new ArmExtensionMove(armExtension,midExtenstion).alongWith(new ArmWristMove(armWrist,midWrist)));
    }
    upperDriver.bButton().whileTrue(seqArmMid);*/

    //TOP ARM BUTTON
    //fixed
    /*seqArmTop = new ArmActuatorMove(armActuator, topHeight);
    seqArmTop = seqArmTop.andThen(new ArmExtensionMove(armExtension,topExtenstion).alongWith(new ArmWristMove(armWrist,topWrist)));
    upperDriver.yButton().whileTrue(seqArmTop);*/

    //SUBSTATION HEIGHT BUTTON
    //fixed
    seqArmLoad = new ArmActuatorMove(armActuator, substationHeight);
    seqArmLoad = seqArmLoad.andThen(new ArmExtensionMove(armExtension,substationExtension).alongWith(new ArmWristMove(armWrist,substationWrist)));
    upperDriver.xButton().whileTrue(seqArmLoad);
  }

  @Override
  public void teleopPeriodic() {
    //FLOOR HEIGHT
    if (armActuator.getPot() > floorHeight) {
      seqArmBot = new ArmExtensionMove(armExtension,floorExtenstion);
      seqArmBot = seqArmBot.andThen(Commands.parallel(new ArmActuatorMove(armActuator, floorHeight), new ArmWristMove(armWrist,floorWrist)));
    } else {
    seqArmBot = new ArmActuatorMove(armActuator, floorHeight);
    seqArmBot = seqArmBot.andThen(Commands.parallel(new ArmExtensionMove(armExtension,floorExtenstion), new ArmWristMove(armWrist,floorWrist)));
    }
    upperDriver.aButton().whileTrue(seqArmBot);

    //MID HEIGHT
    if (armActuator.getPot() > floorHeight) {
      seqArmMid = Commands.parallel(new ArmActuatorMove(armActuator, midHeight), new ArmWristMove(armWrist,midWrist),new ArmExtensionMove(armExtension,midExtenstion));
    } else {
    seqArmMid = new ArmActuatorMove(armActuator, midHeight);
    seqArmMid = seqArmMid.andThen(Commands.parallel(new ArmExtensionMove(armExtension,midExtenstion), new ArmWristMove(armWrist,midWrist)));
    }
    upperDriver.bButton().whileTrue(seqArmMid);

    //TOP HEIGHT
    if (armActuator.getPot() > floorHeight) {
      seqArmTop = Commands.parallel(new ArmActuatorMove(armActuator, topHeight), new ArmWristMove(armWrist,topWrist),new ArmExtensionMove(armExtension,topExtenstion));
    } else {
    seqArmTop = new ArmActuatorMove(armActuator, topHeight);
    seqArmTop = seqArmTop.andThen(Commands.parallel(new ArmExtensionMove(armExtension,topExtenstion), new ArmWristMove(armWrist,topWrist)));
    }
    upperDriver.yButton().whileTrue(seqArmTop);
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

}