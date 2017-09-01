package com.martensigwart.fakeloadapp.server;

import com.martensigwart.fakeload.FakeLoad;
import com.martensigwart.fakeload.FakeLoadExecutor;
import com.martensigwart.fakeload.FakeLoadExecutors;
import com.martensigwart.fakeloadapp.common.FakeLoadMessage;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class FakeLoadConsumer extends DefaultConsumer {

    private static final Logger log = LoggerFactory.getLogger(FakeLoadConsumer.class);


    FakeLoadConsumer(Channel channel) {
        super(channel);
    }


    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body)
            throws IOException {

        // Recreate simulator message from body
        FakeLoadMessage message = FakeLoadMessage.fromBytes(body);
        if (message == null) {
            throw new IOException("Cannot create FakeLoadMessage from body");
        }

        // Run simulation
        log.info(String.format("Retrieved simulator message %s", message));
        executeFakeLoad(message);

        try {
            log.info("Cooling down...");
            Thread.sleep(5000);
            log.info("Waiting for messages...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void executeFakeLoad(FakeLoadMessage message) {

        FakeLoad fakeload = message.getFakeLoad();
        FakeLoadExecutor executor = FakeLoadExecutors.newDefaultExecutor();
        executor.execute(fakeload);

    }


}
