package simulation;

import com.rabbitmq.client.*;
import common.message.SimulatorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by martensigwart on 16.05.17.
 */
public class LoadSimulatorConsumer extends DefaultConsumer {

    private static final Logger log = LoggerFactory.getLogger(LoadSimulatorConsumer.class);

    private Simulation simulation;


    public LoadSimulatorConsumer(Channel channel, Simulation simulation) {
        super(channel);
        this.simulation = simulation;
    }


    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body)
            throws IOException {

        // Recreate simulator message from body
        SimulatorMessage message = SimulatorMessage.fromBytes(body);
        if (message == null) {
            throw new IOException("Cannot create SimulatorMessage from body");
        }

        // run simulation
        try {

            log.info(String.format("Retrieved simulator message %s", message));
            runSimulation(message);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("Waiting for messages...");
        }
    }

    private void runSimulation(SimulatorMessage message) throws InterruptedException {

        simulation.setUp(message);
        simulation.run();
        simulation.cleanUp();

        log.info("Cooling down...");
        Thread.sleep(10000);
    }


}
