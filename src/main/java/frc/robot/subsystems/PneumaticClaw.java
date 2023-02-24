package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Counter;

public class PneumaticClaw {

    private String name;

    private Compressor compressor;

    private Solenoid solenoid;

    private Counter counter;
    
    public PneumaticClaw(String name, int moduleNum, int solenoidChannel, int counterChannel){

        compressor = new Compressor(moduleNum, PneumaticsModuleType.CTREPCM);
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, solenoidChannel);
        counter = new Counter(counterChannel);
    }

    public void solenoidSet(boolean On){
        solenoid.set(On);
    }
}
