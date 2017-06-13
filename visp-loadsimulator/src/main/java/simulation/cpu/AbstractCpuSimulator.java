package simulation.cpu;

import simulation.SimulationLoad;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by martensigwart on 19.05.17.
 */
public abstract class AbstractCpuSimulator implements ICpuSimulator {

    private SimulationLoad load;
    long workload;

    public AbstractCpuSimulator(SimulationLoad load) {
        this.load = load;
        this.workload = load.getCpuLoad();
    }

    @Override
    public String call() throws Exception {

            while (true) {
                // adopt changes to simulation load
                if (load.getCpuLoad() != workload) {
                    workload = load.getCpuLoad();
                }

                long time = System.currentTimeMillis() + workload;
                while (System.currentTimeMillis() < time) {
                    simulateCpu();
                }
                Thread.sleep(100 - workload);
            }

    }

}
