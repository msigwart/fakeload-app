package simulation.cpu;

import simulation.LoadControlObject;

/**
 * Created by martensigwart on 19.05.17.
 */
public abstract class AbstractCpuSimulator implements ICpuSimulator {

    private LoadControlObject load;
    long workload;

    public AbstractCpuSimulator(LoadControlObject load) {
        this.load = load;
        this.workload = load.getInitialWorkload();
    }

    @Override
    public String call() throws Exception {

            while (true) {
                try {
                    // adopt changes to simulation load
                    if (load.getAndDecrementPermits() > 0) {

                        switch (load.getAdjustmentType()) {
                            case INCREASE:
                                if (this.workload < 100) this.workload++;
                                break;
                            case DECREASE:
                                if (this.workload > 0) this.workload--;
                                break;
                        }
                    }

                    long time = System.currentTimeMillis() + workload;
                    while (System.currentTimeMillis() < time) {
                        simulateCpu();
                    }
                    Thread.sleep(100 - workload);
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED CPU SIMULATOR");
                }
            }

    }

}
