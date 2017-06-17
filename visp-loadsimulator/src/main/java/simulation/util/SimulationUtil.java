package simulation.util;

import common.SimulatorMessage;
import common.SimulatorMessagePart;
import common.enums.SimulationScope;
import common.enums.SimulationType;
import simulation.ControlTask;
import simulation.LoadControlObject;
import simulation.cpu.FibonacciCpuSimulator;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;
import simulation.ram.RamSimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by martensigwart on 17.05.17.
 */
public class SimulationUtil {

    public static ICpuSimulator createCpuSimulator(LoadControlObject load) {

        ICpuSimulator cpuSimulator;

        switch (load.getMethod()) {
            case 1:
            case 0:
            default:
                cpuSimulator = new FibonacciCpuSimulator(load);
                break;
        }

        return cpuSimulator;

    }

    public static IRamSimulator createRamSimulator(LoadControlObject load) {
        IRamSimulator ramSimulator;

        switch (load.getMethod()) {
            case 1:
            case 0:
            default:
                ramSimulator = new RamSimulator(load);
                break;
        }

        return ramSimulator;
    }

    public static List<ICpuSimulator> createCpuSimulators(int no, LoadControlObject load) {
        if (no < 1) {
            throw new IllegalArgumentException("Number of CpuSimulators to create has to be greater than zero.");
        }
        List<ICpuSimulator> cpuSimulators = new ArrayList<>();
        for (int i=0; i<no; i++) {
            cpuSimulators.add(createCpuSimulator(load));
        }
        return cpuSimulators;
    }

    public static ControlTask createSimulationControl(SimulatorMessage message, SimulationScope scope, Integer noCores) {
        ControlTask control = new ControlTask(scope, noCores);

        message.getParts().forEach((type, part) -> {
            control.setInitialLoad(type, part.getWorkload(), part.getMethod());
        });


        return control;
    }
}
