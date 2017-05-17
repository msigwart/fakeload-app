package simulation;

import common.SimulatorMessage;
import common.enums.SimulationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;

import javax.annotation.PostConstruct;

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
    private ICpuSimulator cpuSimulator;
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
            cpuSimulator = simulatorFactory.createCpuSimulator(message.getParts().get(SimulationType.CPU));
        }


        // setup RAM Simulator
        if (message.getParts().containsKey(SimulationType.RAM)) {
            ramSimulator = simulatorFactory.createRamSimulator(message.getParts().get(SimulationType.RAM));
        }

    }

    @Override
    public void runSimulation() {
        if (ramSimulator != null) {
            ramSimulator.allocateMemory();
        }

        if (cpuSimulator != null) {
            cpuSimulator.simulateCpu();
        }
    }
}
