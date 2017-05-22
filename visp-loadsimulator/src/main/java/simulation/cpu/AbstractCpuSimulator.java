package simulation.cpu;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by martensigwart on 19.05.17.
 */
public abstract class AbstractCpuSimulator implements ICpuSimulator {

    private Integer workload;

    public AbstractCpuSimulator(Integer workload) {
        this.workload = workload;
    }

    @Override
    public String call() throws Exception {

            while (true) {
                long time = System.currentTimeMillis() + workload;
                while (System.currentTimeMillis() < time) {
                    simulateCpu();
                }
                Thread.sleep(100 - workload);
            }

    }

}
