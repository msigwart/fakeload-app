package simulation.cpu;

import common.message.IWorkload;
import simulation.LoadControlObject;

/**
 * Created by martensigwart on 19.05.17.
 */
public abstract class AbstractCpuSimulator implements ICpuSimulator {

    private LoadControlObject load;
    int workload;

    public AbstractCpuSimulator(LoadControlObject load) {
        this.load = load;
        this.workload = load.getInitialWorkload().getValue();
    }

    @Override
    public String call() throws Exception {
        try {

            while (true) {

                // adopt changes to simulation load
                if (load.getAndDecrementPermits() > 0) {

                    switch (load.getAdjustmentType()) {
                        case INCREASE:
                            if (workload < 100) workload++;
                            break;
                        case DECREASE:
                            if (workload > 0) workload--;
                            break;
                    }
                }

                long time = System.currentTimeMillis() + workload;
                while (System.currentTimeMillis() < time) {
                    simulateCpu();
                }
                Thread.sleep(100 - workload);
            }

        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED CPU SIMULATOR");
        }

        return null;

    }

}
