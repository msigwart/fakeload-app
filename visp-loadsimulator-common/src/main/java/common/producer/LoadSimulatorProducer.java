package common.producer;

import common.LoadSimulatorClient;
import common.SimulatorMessage;

import java.io.IOException;

/**
 * Created by martensigwart on 03.05.17.
 */
public class LoadSimulatorProducer extends LoadSimulatorClient implements ILoadSimulatorProducer {

    public LoadSimulatorProducer() {
        super();
    }

    public void sendLoadSimulationMessage(SimulatorMessage message) throws IOException {

        channel.basicPublish("", queue, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

    }

}
