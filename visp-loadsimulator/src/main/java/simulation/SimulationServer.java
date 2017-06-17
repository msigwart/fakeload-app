package simulation;

import common.SimulatorMessage;
import common.consumer.LoadSimulatorConsumer;
import common.enums.SimulationScope;
import common.util.MyCommandLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by martensigwart on 16.05.17.
 */
@Component
public class SimulationServer implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(SimulationServer.class);

    @Autowired
    private Simulation simulation;


    @Override
    public void run(String... args) throws Exception {

        try {

            // Parse command line arguments
            MyCommandLineParser parser = new MyCommandLineParser(args);
            String host = parser.parseHost();
            String queue = parser.parseQueue();
            SimulationScope scope = parser.parseScope();
            Boolean controlDisabled = parser.parseControlDisabled();

            LoadSimulatorConsumer consumer = new LoadSimulatorConsumer();
            consumer.connect(host, queue);

            log.info("Connected to RabbitMQ at {}, queue: {}, simulation scope: {}{}", host, queue, scope,
                    controlDisabled ? ", control thread disabled" : "");


            log.info("Waiting for messages...");
            SimulatorMessage message;
            while (true) {
                message = consumer.retrieveSimulatorMessage();
                if (message != null) {
                    log.info(String.format("Retrieved simulator message %s", message));

                    simulation.setUp(message, scope, controlDisabled);
                    simulation.run();
                    
                    log.info("Cooling down...");
                    Thread.sleep(10000);
                    log.info("Waiting for messages...");

                } else {
                    Thread.sleep(5000);
                }
            }

        } catch (InterruptedException | IOException | TimeoutException e) {
            log.error("Caught Exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

}
