package simulation.ram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulation.LoadControlObject;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;

/**
 * Created by martensigwart on 17.05.17.
 */
public class RamSimulator implements IRamSimulator {

    private static final Logger log = LoggerFactory.getLogger(RamSimulator.class);

    private LoadControlObject load;
    private Integer workload;
    private SimulatedMemory memory;


    public RamSimulator(LoadControlObject load) {
        this.load = load;
        this.workload = load.getInitialWorkload();
        this.memory = new SimulatedMemory();
    }


    @Override
    public String call() throws Exception {

        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        log.info("Max Memory: {} Total Memory: {}, Free Memory: {}",
                maxMemory / (1024 * 1024),
                totalMemory / (1024 * 1024),
                freeMemory / (1024 * 1024));

        System.out.println("Runtime max: " + mb(Runtime.getRuntime().maxMemory()));
        MemoryMXBean m = ManagementFactory.getMemoryMXBean();

        System.out.println("Non-heap: " + mb(m.getNonHeapMemoryUsage().getMax()));
        System.out.println("Heap: " + mb(m.getHeapMemoryUsage().getMax()));

        for (MemoryPoolMXBean mp : ManagementFactory.getMemoryPoolMXBeans()) {
            System.out.println("Pool: " + mp.getName() +
                    " (type " + mp.getType() + ")" +
                    " = " + mb(mp.getUsage().getMax()));
        }

        memory.allocateMemory(512);


        freeMemory = Runtime.getRuntime().freeMemory();
        totalMemory = Runtime.getRuntime().totalMemory();
        maxMemory = Runtime.getRuntime().maxMemory();

        log.info("Max Memory: {} Total Memory: {}, Free Memory: {}",
                maxMemory / (1024 * 1024),
                totalMemory / (1024 * 1024),
                freeMemory / (1024 * 1024));

        while (true) {
            synchronized (load) {
                try {
                    load.wait();
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED!!!");
                    memory.removeAll();
                    break;
                }
            }
        }

        return null;
    }

    private String mb(long s) {
        return String.format("%d (%.2f M)", s, (double)s / (1024 * 1024));
    }

    @Override
    public void allocateMemory() {

    }
}
