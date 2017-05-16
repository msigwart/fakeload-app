package client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import common.Constants;
import common.SimulatorMessage;
import common.SimulationType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by martensigwart on 03.05.17.
 */
public class LoadSimulatorClient {

    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private String queue;

    public LoadSimulatorClient() {
        factory = new ConnectionFactory();

    }

    public void connect(String host, String queue) throws IOException, TimeoutException {

        // check if a connection is open
        if (connection != null && connection.isOpen()) {
            connection.close();
        }

        // create new connection
        factory.setHost(host);
        connection = factory.newConnection();
        channel = connection.createChannel();
        this.queue = queue;

        // declare queue
        channel.queueDeclare(queue, false, false, false, null);

    }

    public void sendLoadSimulationMessage(SimulatorMessage message) throws IOException {

        channel.basicPublish("", queue, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }

}
