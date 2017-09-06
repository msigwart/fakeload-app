package com.martensigwart.fakeloadapp.server;

import com.martensigwart.fakeloadapp.common.MyCommandLineParser;
import com.martensigwart.fakeloadapp.common.RabbitMQClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class Server extends RabbitMQClient implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(Server.class);


    @Override
    public void run(String... args) throws Exception {

        try {

            // Parse command line arguments
            MyCommandLineParser parser = new MyCommandLineParser(args);
            String host = parser.parseHost();
            String queue = parser.parseQueue();


            // Connecting to RabbitMQ
            log.info("Trying to connect to RabbitMQ at {}, queue: {}", host, queue);

            connect(host, queue);

            log.info("Connected to RabbitMQ");

            FakeLoadConsumer consumer = new FakeLoadConsumer(this.channel);

            log.info("Creating consumer, waiting for messages...");
            this.channel.basicConsume(queue, true, consumer);

        } catch (IOException | TimeoutException e) {
            log.error("Caught Exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

}
