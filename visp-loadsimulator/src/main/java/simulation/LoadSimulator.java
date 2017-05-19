package simulation;

import common.SimulatorMessage;
import common.enums.SimulationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import simulation.cpu.AbstractCpuSimulator;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by martensigwart on 17.05.17.
 */
@Component
public class LoadSimulator implements ILoadSimulator {

    private static final Logger log = LoggerFactory.getLogger(LoadSimulator.class);

    private static LoadSimulator instance;

    public static LoadSimulator getInstance() {
        if (instance == null) {
            instance = new LoadSimulator();
        }
        return instance;
    }

    private SimulatorFactory simulatorFactory;
    private Integer duration;
    private AbstractCpuSimulator cpuSimulator;
    private IRamSimulator ramSimulator;


    @PostConstruct
    public void init() {
        simulatorFactory = new SimulatorFactory();
    }

    @Override
    public void setUpSimulation(SimulatorMessage message) {
        duration = message.getDuration();

        // setup CPU Simulator
        if (message.getParts().containsKey(SimulationType.CPU)) {
            cpuSimulator = (AbstractCpuSimulator)simulatorFactory.createCpuSimulator(message.getParts().get(SimulationType.CPU));
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

        if (cpuSimulator != null) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(cpuSimulator);
            try {
                log.info(String.format("Running CPU Simulation for %d seconds...", duration));
                future.get(duration, TimeUnit.SECONDS);

            } catch (TimeoutException e) {
                log.info("CPU Simulation ended.");
                future.cancel(true);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        log.info("++ Simulation ended ++");
    }
}
