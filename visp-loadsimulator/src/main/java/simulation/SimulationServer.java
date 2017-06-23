package simulation;

import common.enums.SimulationScope;
import common.util.MyCommandLineParser;
import common.util.RabbitMQClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by martensigwart on 16.05.17.
 */
@Component
public class SimulationServer extends RabbitMQClient implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(SimulationServer.class);


    @Override
    public void run(String... args) throws Exception {

        try {

            // Parse command line arguments
            MyCommandLineParser parser = new MyCommandLineParser(args);
            String host = parser.parseHost();
            String queue = parser.parseQueue();
            SimulationScope scope = parser.parseScope();
            Boolean controlDisabled = parser.parseControlDisabled();


            // Connecting to RabbitMQ
            log.info("Trying to connect to RabbitMQ at {}, queue: {}, simulation scope: {}{}", host, queue, scope,
                    controlDisabled ? ", control thread disabled" : "");

            connect(host, queue);

            log.info("Connected to RabbitMQ");


            Simulation simulation = new Simulation();
            simulation.init();
            simulation.setScope(scope);
            simulation.setControlDisabled(controlDisabled);
            LoadSimulatorConsumer consumer = new LoadSimulatorConsumer(this.channel, simulation);

            log.info("Creating consumer, waiting for messages...");
            this.channel.basicConsume(queue, true, consumer);

        } catch (IOException | TimeoutException e) {
            log.error("Caught Exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

}
