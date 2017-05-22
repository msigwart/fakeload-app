package simulation;

import common.SimulatorMessagePart;
import common.enums.SimulationType;
import simulation.cpu.FibonacciCpuSimulator;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;
import simulation.ram.RamSimulator;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by martensigwart on 17.05.17.
 */
public class SimulatorFactory {

    public ICpuSimulator createCpuSimulator(SimulatorMessagePart messagePart) {
        if (messagePart.getType() != SimulationType.CPU) {
            throw new IllegalArgumentException("SimulatorMessagePart is not of type CPU");
        }
        ICpuSimulator cpuSimulator;

        switch (messagePart.getMethod()) {
            case 1:
            case 0:
            default:
                cpuSimulator = new FibonacciCpuSimulator(messagePart.getWorkload());
                break;
        }

        return cpuSimulator;

    }

    public IRamSimulator createRamSimulator(SimulatorMessagePart messagePart) {
        if (messagePart.getType() != SimulationType.RAM) {
            throw new IllegalArgumentException("SimulatorMessagePart is not of type RAM");
        }
        IRamSimulator ramSimulator;

        switch (messagePart.getMethod()) {
            case 1:
            case 0:
            default:
                ramSimulator = new RamSimulator(messagePart.getWorkload());
                break;
        }

        return ramSimulator;
    }

    public List<ICpuSimulator> createCpuSimulators(int no, SimulatorMessagePart simulatorMessagePart) {
        if (no < 1) {
            throw new IllegalArgumentException("Number of CpuSimulators to create has to be greater than zero.");
        }
        List<ICpuSimulator> cpuSimulators = new ArrayList<>();
        for (int i=0; i<no; i++) {
            cpuSimulators.add(createCpuSimulator(simulatorMessagePart));
        }
        return cpuSimulators;
    }
}
