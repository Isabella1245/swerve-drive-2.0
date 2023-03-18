package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PneumaticClaw extends SubsystemBase implements Constants {

    private String name;
    private Compressor compressor;
    private Solenoid solenoid;
    private Counter counter;
    boolean isClawDone = false;
    
    public PneumaticClaw(){
        compressor = new Compressor(compressorModule, PneumaticsModuleType.CTREPCM);
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, solenoidChannel);
        counter = new Counter(counterChannel);
    }

    public void solenoidSet(boolean On){
        solenoid.set(On);
    }

    //used in Teleop
    public void moveClaw(boolean open, boolean close){
        if (open == true) {solenoidSet(true);}
        if (close == true) {solenoidSet(false);}
    }

    public void openClaw(){
        solenoidSet(true);
        isClawDone = true;
    }

    public void closeClaw(){
        solenoidSet(false);
        isClawDone = true;
    }

    public boolean isClawFinished() {
        return isClawDone;
    }
}
