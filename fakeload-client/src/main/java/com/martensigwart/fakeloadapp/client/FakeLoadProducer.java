package com.martensigwart.fakeloadapp.client;

import com.martensigwart.fakeloadapp.common.FakeLoadMessage;
import com.martensigwart.fakeloadapp.common.RabbitMQClient;

import java.io.IOException;

public class FakeLoadProducer extends RabbitMQClient {

    public FakeLoadProducer() {
        super();
    }

    public void sendFakeLoadMessage(FakeLoadMessage message) throws IOException {

        channel.basicPublish("", queue, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

    }

}
