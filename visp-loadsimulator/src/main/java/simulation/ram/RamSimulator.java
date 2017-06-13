package simulation.ram;

import simulation.SimulationLoad;

/**
 * Created by martensigwart on 17.05.17.
 */
public class RamSimulator implements IRamSimulator {

    private SimulationLoad load;
    private Integer workload;


    public RamSimulator(SimulationLoad load) {
        this.load = load;
        this.workload = load.getRamLoad();
    }

    @Override
    public void allocateMemory() {

    }

    @Override
    public String call() throws Exception {
        return null;
    }

}
