package simulation;

import common.SimulatorMessage;

/**
 * Created by martensigwart on 17.05.17.
 */
public interface ILoadSimulator {

    void setUpSimulation(SimulatorMessage message);

    void runSimulation();
}
