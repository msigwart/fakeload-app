package simulation;

import common.SimulatorMessage;
import common.enums.SimulationScope;
import common.enums.SimulationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;
import simulation.util.SimulationUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by martensigwart on 17.05.17.
 */
@Component
public class Simulation implements ISimulation {


    private static final Logger log = LoggerFactory.getLogger(Simulation.class);

    private Integer duration;
    private Integer noCores;
    private ControlTask simulationControl;
    private List<Callable<String>> allTasks;


    @PostConstruct
    public void init() {
        noCores = Runtime.getRuntime().availableProcessors();
        allTasks = new ArrayList<>();
    }

    @Override
    public void setUp(SimulatorMessage message, SimulationScope scope, Boolean controlDisabled) {
        duration = message.getDuration();
        simulationControl = SimulationUtil.createSimulationControl(message, scope, noCores);

        // add control task if not disabled
        if (!controlDisabled) {
            allTasks.add(simulationControl);
        }

        // setup CPU Simulator
        if (simulationControl.containsCpu()) {
            List<ICpuSimulator> cpuSimulators;
            cpuSimulators = SimulationUtil.createCpuSimulators(noCores, simulationControl.getLoad(SimulationType.CPU));
            allTasks.addAll(cpuSimulators);
        }

        // setup RAM Simulator
        if (simulationControl.containsRam()) {
            IRamSimulator ramSimulator;
            ramSimulator = SimulationUtil.createRamSimulator(simulationControl.getLoad(SimulationType.RAM));
            allTasks.add(ramSimulator);
        }

    }

    @Override
    public void run() {
        log.info(String.format("+++ Running Simulation on %d cores for %d seconds +++", noCores, duration));

        ExecutorService executor = Executors.newFixedThreadPool(allTasks.size());

        try {
            List<Future<String>> futures = executor.invokeAll(allTasks, duration, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        log.info("Cleaning up simulation...");
        cleanUp();
        log.info("++ Simulation ended ++");
    }

    @PreDestroy
    private void cleanUp() {
        allTasks.clear();
    }
}
