package simulation;

import common.SimulatorMessage;
import common.enums.SimulationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by martensigwart on 17.05.17.
 */
@Component
public class LoadSimulator implements ILoadSimulator {

    private static final Logger log = LoggerFactory.getLogger(LoadSimulator.class);

    private SimulatorFactory simulatorFactory;
    private Integer duration;
    private Integer noCores;
    private List<ICpuSimulator> cpuSimulators;
    private IRamSimulator ramSimulator;


    @PostConstruct
    public void init() {
        simulatorFactory = new SimulatorFactory();
        noCores = Runtime.getRuntime().availableProcessors();
    }

    @Override
    public void setUpSimulation(SimulatorMessage message) {
        duration = message.getDuration();

        // setup CPU Simulator
        if (message.getParts().containsKey(SimulationType.CPU)) {
            cpuSimulators = simulatorFactory.createCpuSimulators(noCores, message.getParts().get(SimulationType.CPU));
        }


        // setup RAM Simulator
        if (message.getParts().containsKey(SimulationType.RAM)) {
            ramSimulator = simulatorFactory.createRamSimulator(message.getParts().get(SimulationType.RAM));
        }

    }

    @Override
    public void runSimulation() {
        log.info("++ Simulation started ++");

        if (ramSimulator != null) {
            ramSimulator.allocateMemory();
        }

        if (cpuSimulators != null) {
//            ExecutorService executor = Executors.newSingleThreadExecutor();
            ExecutorService executor = Executors.newFixedThreadPool(noCores);

            try {
                log.info(String.format("Running CPU Simulation on %d cores for %d seconds...", noCores, duration));

                List<Future<String>> futures = executor.invokeAll(cpuSimulators, duration, TimeUnit.SECONDS);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }

        }
        log.info("CPU Simulation has ended.");
        log.info("Cleaning up simulation...");
        cleanUp();
        log.info("++ Simulation ended ++");
    }

    @PreDestroy
    private void cleanUp() {
//        cpuSimulators
    }
}
