package client.producer;

import common.message.SimulatorMessage;

import java.io.IOException;

/**
 * Created by martensigwart on 16.05.17.
 */
public interface ILoadSimulatorProducer {

    void sendLoadSimulationMessage(SimulatorMessage message) throws IOException;

}
