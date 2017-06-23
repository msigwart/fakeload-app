package simulation;

import common.message.SimulatorMessage;

/**
 * Created by martensigwart on 17.05.17.
 */
public interface ISimulation {

    void setUp(SimulatorMessage message);

    void run();
}
