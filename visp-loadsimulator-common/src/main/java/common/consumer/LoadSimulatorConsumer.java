package common.consumer;

import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.GetResponse;
import common.LoadSimulatorClient;
import common.SimulatorMessage;

import java.io.IOException;

/**
 * Created by martensigwart on 16.05.17.
 */
public class LoadSimulatorConsumer extends LoadSimulatorClient implements ILoadSimulatorConsumer {


    public LoadSimulatorConsumer() {
        super();
    }

    @Override
    public SimulatorMessage retrieveSimulatorMessage() throws IOException {
        boolean autoAck = true;
        GetResponse response = channel.basicGet(queue, autoAck);
        return SimulatorMessage.fromBytes(response.getBody());
    }

}
