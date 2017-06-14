package simulation;

import common.SimulatorMessage;
import common.enums.SimulationScope;

/**
 * Created by martensigwart on 17.05.17.
 */
public interface ISimulation {

    void setUp(SimulatorMessage message, SimulationScope scope, Boolean controlDisabled);

    void run();
}
