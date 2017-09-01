package com.martensigwart.fakeloadapp.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class RabbitMQClient {

    private final ConnectionFactory factory;
    private Connection connection;
    protected Channel channel;
    protected String queue;

    public RabbitMQClient() {
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
