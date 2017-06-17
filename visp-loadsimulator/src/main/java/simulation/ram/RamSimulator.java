package simulation.ram;

import simulation.LoadControlObject;

/**
 * Created by martensigwart on 17.05.17.
 */
public class RamSimulator implements IRamSimulator {

    private LoadControlObject load;
    private Integer workload;


    public RamSimulator(LoadControlObject load) {
        this.load = load;
        this.workload = load.getInitialWorkload();
    }

    @Override
    public void allocateMemory() {

    }

    @Override
    public String call() throws Exception {
        return null;
    }

}
