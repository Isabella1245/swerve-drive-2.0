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
import frc.robot.commands.TurnRobot;
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
  //Command seqArmBot, seqArmMid, seqArmTop, seqArmLoad;
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
    
    Command scoreTop, shortMobility, sequence, longMobility, rampMobility;
    scheduler.cancelAll();
    autonSelection = autonChooser.getSelected();
    dockSelection = dockChooser.getSelected();

    double manualAutonSelection = 1;

    scoreTop = new ArmActuatorMove(armActuator, topHeight);
    scoreTop = scoreTop.andThen(new ArmExtensionMove(armExtension,topExtenstion));
    scoreTop = scoreTop.andThen(new ArmWristMove(armWrist,topWrist));
    scoreTop = scoreTop.andThen(new OpenClaw(claw));

    shortMobility = new DriveSegment(swerve, B1MobSpeed, B1MobAngle, B1MobTime);
    shortMobility = shortMobility.andThen(new ArmExtensionMove(armExtension, 0));
    //determine if arm extension elevator resets to zero in the right spot in teleop 
    longMobility = new DriveSegment(swerve, B3MobSpeed, B3MobAngle, B3MobTime);
    longMobility = longMobility.andThen(new ArmExtensionMove(armExtension, 0));

    rampMobility = new ArmExtensionMove(armExtension, 0);
    //rampMobility = rampMobility.andThen(new TurnRobot(swerve));
    //rampMobility = rampMobility.andThen(new DriveSegment(swerve,B2MobSpeed, B2MobAngle, B2MobTime));
    //rampMobility = rampMobility.andThen(new BalanceRobot(swerve));
// Cole made this
    //SCORE + MOBILITY
    /*B1/R3: Score + Mobility*/
    if (manualAutonSelection == (kScoreMobilityB1)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityB1);
      sequence = scoreTop;
      sequence = sequence.andThen(shortMobility);
     //B2/R2: Score + Ramp = ?
    } else if (manualAutonSelection == (kScoreMobilityB2)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityB2);
      sequence = scoreTop;
      sequence = sequence.andThen(rampMobility);
    //B3/R1: Score + Mobility
    } else if (manualAutonSelection == (kScoreMobilityB3)) {
      SmartDashboard.putNumber("Auton Program:", kScoreMobilityB3);
      sequence = scoreTop;
      sequence = sequence.andThen(longMobility);

    
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
    //no safety feature
    
    seqArmBot = Commands.parallel(new ArmActuatorMove(armActuator, floorHeight), new ArmExtensionMove(armExtension, floorExtenstion), new ArmWristMove(armWrist, floorWrist));
    upperDriver.aButton().whileTrue(seqArmBot);

    //MID HEIGHT BUTTON
    //no safety feature

    seqArmMid = Commands.parallel(new ArmExtensionMove(armExtension, midExtenstion), new ArmActuatorMove(armActuator, midHeight), new ArmWristMove(armWrist,midWrist));
    upperDriver.bButton().whileTrue(seqArmMid);

    //TOP ARM BUTTON
    //no safety feature
    seqArmTop = Commands.parallel(new ArmExtensionMove(armExtension, topExtenstion), new ArmActuatorMove(armActuator, topHeight)).andThen(new ArmWristMove(armWrist,topWrist));
    upperDriver.yButton().whileTrue(seqArmTop);

    //SUBSTATION HEIGHT BUTTON
    //no safety feature
    seqArmLoad = Commands.parallel(new ArmExtensionMove(armExtension, substationExtension), new ArmActuatorMove(armActuator, substationHeight)).andThen(new ArmWristMove(armWrist,substationWrist));
    upperDriver.xButton().whileTrue(seqArmLoad);
  }

  @Override
  public void teleopPeriodic() {
    //FLOOR HEIGHT
    //test one more time to see if drive jitters 
    /*
    if (armActuator.getPot() > floorHeight) {
      seqArmBot = new ArmExtensionMove(armExtension,floorExtenstion);
      seqArmBot = seqArmBot.andThen(Commands.parallel(new ArmActuatorMove(armActuator, floorHeight), new ArmWristMove(armWrist,floorWrist)));
    } else {
    seqArmBot = new ArmActuatorMove(armActuator, floorHeight);
    seqArmBot = seqArmBot.andThen(Commands.parallel(new ArmExtensionMove(armExtension,floorExtenstion), new ArmWristMove(armWrist,floorWrist)));
    }
    //upperDriver.aButton().whileTrue(seqArmBot);
    

    //MID HEIGHT
    if (armActuator.getPot() > floorHeight) {
      seqArmMid = Commands.parallel(new ArmActuatorMove(armActuator, midHeight), new ArmWristMove(armWrist,midWrist),new ArmExtensionMove(armExtension,midExtenstion));
    } else {
    seqArmMid = new ArmActuatorMove(armActuator, midHeight);
    seqArmMid = seqArmMid.andThen(Commands.parallel(new ArmExtensionMove(armExtension,midExtenstion), new ArmWristMove(armWrist,midWrist)));
    }
    //upperDriver.bButton().whileTrue(seqArmMid);

    //TOP HEIGHT
    if (armActuator.getPot() > floorHeight) {
      seqArmTop = Commands.parallel(new ArmActuatorMove(armActuator, topHeight), new ArmWristMove(armWrist,topWrist),new ArmExtensionMove(armExtension,topExtenstion));
    } else {
    seqArmTop = new ArmActuatorMove(armActuator, topHeight);
    seqArmTop = seqArmTop.andThen(Commands.parallel(new ArmExtensionMove(armExtension,topExtenstion), new ArmWristMove(armWrist,topWrist)));
    }
    //upperDriver.yButton().whileTrue(seqArmTop);
    */
    
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

}