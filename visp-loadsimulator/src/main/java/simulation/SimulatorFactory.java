package simulation;

import common.SimulatorMessagePart;
import common.enums.SimulationType;
import simulation.cpu.FibonacciCpuSimulator;
import simulation.cpu.ICpuSimulator;
import simulation.ram.IRamSimulator;
import simulation.ram.RamSimulator;

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
}
