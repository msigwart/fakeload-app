package simulation.ram;

import common.message.IWorkload;
import common.message.RamWorkload;
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

    private static final long KB = 1024;
    private static final long MB = KB*1024;
    private static final long GB = MB*1024;

    private static final long OUT_OF_MEMORY_SAFETYNET = 100*MB;


    private LoadControlObject load;
    private IWorkload workload;
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

        long usedMemory = totalMemory - freeMemory;
        long availableMemory = maxMemory - usedMemory;

        log.info("Max Memory: {} Total Memory: {}, Free Memory: {}, Available Memory {}",
                mb(maxMemory),
                mb(totalMemory),
                mb(freeMemory),
                mb(availableMemory));

        long desiredMemory = convertToByte(workload);


        if (desiredMemory >= maxMemory - OUT_OF_MEMORY_SAFETYNET) {
            log.warn("Not enough memory for memory simulation of {} bytes (max: {})", mb(desiredMemory), mb(maxMemory));
            return null;
            // do something, maybe throw exception? Or simply return?
        }

        long missingMemory = desiredMemory - usedMemory;

        log.info("Desired Memory: {}, Used Memory: {}, Missing Memory: {}", mb(desiredMemory), mb(usedMemory), mb(missingMemory));

//        System.out.println("Runtime max: " + mb(Runtime.getRuntime().maxMemory()));
//        MemoryMXBean m = ManagementFactory.getMemoryMXBean();
//
//        System.out.println("Non-heap: " + mb(m.getNonHeapMemoryUsage().getMax()));
//        System.out.println("Heap: " + mb(m.getHeapMemoryUsage().getMax()));
//
//        for (MemoryPoolMXBean mp : ManagementFactory.getMemoryPoolMXBeans()) {
//            System.out.println("Pool: " + mp.getName() +
//                    " (type " + mp.getType() + ")" +
//                    " = " + mb(mp.getUsage().getMax()));
//        }

        log.info("Trying to allocate {} bytes", mb(missingMemory));
        memory.allocateMemory(missingMemory);

        freeMemory = Runtime.getRuntime().freeMemory();
        totalMemory = Runtime.getRuntime().totalMemory();
        maxMemory = Runtime.getRuntime().maxMemory();
        usedMemory = totalMemory - freeMemory;
        availableMemory = maxMemory - usedMemory;


        log.info("Max Memory: {} Total Memory: {}, Free Memory: {}, Used Memory {}, Available Memory {}",
                mb(maxMemory),
                mb(totalMemory),
                mb(freeMemory),
                mb(usedMemory),
                mb(availableMemory));

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


    private long convertToByte(IWorkload workload) {
        switch (workload.getUnit()) {

            case PERCENT:
                long maxMemory = Runtime.getRuntime().maxMemory();
                return (long) (maxMemory * ((double)workload.getValue()/100));

            case BYTE:
                return workload.getValue();

            case KB:
                return workload.getValue()*KB;

            case MB:
                return workload.getValue()*MB;

            case GB:
                return workload.getValue()*GB;
        }

        return 0;
    }

    private String mb(long s) {
        return String.format("%d (%.2f M)", s, (double)s / (MB));
    }

    @Override
    public void allocateMemory() {

    }
}
