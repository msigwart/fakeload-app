package simulation.util;

import common.SimulatorMessage;
import common.SimulatorMessagePart;
import common.enums.SimulationScope;
import common.enums.SimulationType;
import simulation.ControlTask;
import simulation.SimulationLoad;
import simulation.cpu.FibonacciCpuSimulator;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;
import simulation.ram.RamSimulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martensigwart on 17.05.17.
 */
public class SimulationUtil {

    public static ICpuSimulator createCpuSimulator(Integer method, SimulationLoad load) {

        ICpuSimulator cpuSimulator;

        switch (method) {
            case 1:
            case 0:
            default:
                cpuSimulator = new FibonacciCpuSimulator(load);
                break;
        }

        return cpuSimulator;

    }

    public static IRamSimulator createRamSimulator(Integer method, SimulationLoad load) {
        IRamSimulator ramSimulator;

        switch (method) {
            case 1:
            case 0:
            default:
                ramSimulator = new RamSimulator(load);
                break;
        }

        return ramSimulator;
    }

    public static List<ICpuSimulator> createCpuSimulators(int no, Integer method, SimulationLoad load) {
        if (no < 1) {
            throw new IllegalArgumentException("Number of CpuSimulators to create has to be greater than zero.");
        }
        List<ICpuSimulator> cpuSimulators = new ArrayList<>();
        for (int i=0; i<no; i++) {
            cpuSimulators.add(createCpuSimulator(method, load));
        }
        return cpuSimulators;
    }

    public static ControlTask createSimulationControl(SimulatorMessage message, SimulationScope scope) {
        ControlTask control = new ControlTask(scope);

        message.getParts().forEach((type, part) -> {
            control.setLoadParameters(type, part.getWorkload(), part.getMethod());
        });

        return control;
    }
}
