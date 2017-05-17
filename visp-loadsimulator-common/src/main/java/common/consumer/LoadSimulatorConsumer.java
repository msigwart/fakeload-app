package common.consumer;

import com.rabbitmq.client.GetResponse;
import common.LoadSimulatorClient;
import common.SimulatorMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by martensigwart on 16.05.17.
 */
@Component
public class LoadSimulatorConsumer extends LoadSimulatorClient implements ILoadSimulatorConsumer {


    public LoadSimulatorConsumer() {
        super();
    }

    @Override
    public SimulatorMessage retrieveSimulatorMessage() throws IOException {
        SimulatorMessage message = null;
        boolean autoAck = true;
        GetResponse response = null;
        if (channel != null) {
             response = channel.basicGet(queue, autoAck);
        }
        if (response != null) {
            message = SimulatorMessage.fromBytes(response.getBody());
        }
        return message;
    }

}
