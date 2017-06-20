package simulation.ram;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by martensigwart on 20.06.17.
 */
public class SimulatedMemory {

    List<byte[]> usedMemory;

    public SimulatedMemory() {
        this.usedMemory = new LinkedList<>();
    }

    public void allocateMemory(Integer noOfMB) {
        for (int i=0; i<noOfMB; i++) {
            this.usedMemory.add(new byte[1000*1000]);
        }
    }

    public void dropMemory(Integer noOfMB) {
        for (int i=0; i<noOfMB; i++) {
            this.usedMemory.remove(this.usedMemory.size()-1);
        }
    }
}
