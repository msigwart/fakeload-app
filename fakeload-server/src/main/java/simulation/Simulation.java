package simulation;

import common.message.SimulatorMessage;
import common.enums.SimulationScope;
import common.enums.SimulationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import simulation.cpu.AbstractCpuSimulator;
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
    private static final SimulationScope DEFAULT_SCOPE = SimulationScope.PROCESS;


    /**
     * The scope defines whether the simulation is run with respect to the process or with respect to the whole system.
     */
    private SimulationScope scope;

    /**
     * Boolean to decide whether or not a control task is started. If the control task is enabled (default), it checks
     * in regular intervals difference between actual load and desired load and forwards instructions to the simulator tasks
     * to adjust the load accordingly.
     */
    private Boolean controlDisabled;
    private Integer duration;
    private Integer noCores;
    private List<Callable<String>> simulatorTasks;



    @PostConstruct
    public void init() {
        noCores = Runtime.getRuntime().availableProcessors();
        simulatorTasks = new ArrayList<>();
        controlDisabled = false;
        scope = DEFAULT_SCOPE;
    }


    @Override
    public void setUp(SimulatorMessage message) {
        duration = message.getDuration();
        ControlTask simulationControl = SimulationUtil.createSimulationControl(message, scope, noCores);

        // add control task if not disabled
        if (!controlDisabled) {
            simulatorTasks.add(simulationControl);
        }

        // setup CPU Simulator
        if (simulationControl.containsCpu()) {
            List<ICpuSimulator> cpuSimulators;
            cpuSimulators = SimulationUtil.createCpuSimulators(noCores, simulationControl.getLoad(SimulationType.CPU));
            simulatorTasks.addAll(cpuSimulators);
        }

        // setup RAM Simulator
        if (simulationControl.containsRam()) {
            IRamSimulator ramSimulator;
            ramSimulator = SimulationUtil.createRamSimulator(simulationControl.getLoad(SimulationType.RAM));
            simulatorTasks.add(ramSimulator);
        }

    }




    @Override
    public void run() {
        log.info(String.format("+++ Running Simulation on %d cores for %d seconds +++", noCores, duration));

        ExecutorService executor = Executors.newFixedThreadPool(simulatorTasks.size());

        try {
            List<Future<String>> futures = executor.invokeAll(simulatorTasks, duration, TimeUnit.SECONDS);
            futures.forEach(stringFuture -> stringFuture.cancel(true));

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
    public void cleanUp() {
        simulatorTasks.clear();
        System.gc();
        AbstractCpuSimulator.resetIdCounter();
    }

    public void setControlDisabled(Boolean controlDisabled) {
        this.controlDisabled = controlDisabled;
    }

    public void setScope(SimulationScope scope) {
        this.scope = scope;
    }
}
