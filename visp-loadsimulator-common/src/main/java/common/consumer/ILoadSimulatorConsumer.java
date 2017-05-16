package common.consumer;

import common.SimulatorMessage;

import java.io.IOException;

/**
 * Created by martensigwart on 16.05.17.
 */
public interface ILoadSimulatorConsumer {

    SimulatorMessage retrieveSimulatorMessage() throws IOException;
}
