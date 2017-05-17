package simulation.cpu;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by martensigwart on 17.05.17.
 */
public class CpuSimulator implements ICpuSimulator {

    private static final Logger log = LoggerFactory.getLogger(CpuSimulator.class);

    private Integer workload;

    public CpuSimulator(Integer workload) {
        this.workload = workload;
    }

    @Override
    public void simulateCpu() {
        log.info("Now simulating CPU....");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
