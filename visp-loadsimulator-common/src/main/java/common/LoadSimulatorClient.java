package common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by martensigwart on 16.05.17.
 */
public abstract class LoadSimulatorClient {

    private final ConnectionFactory factory;
    private Connection connection;
    protected Channel channel;
    protected String queue;

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
}
