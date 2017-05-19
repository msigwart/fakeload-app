package simulation;

import common.SimulatorMessage;
import common.consumer.LoadSimulatorConsumer;
import common.util.Constants;
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
public class SimulationController implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(SimulationController.class);

    @Autowired
    private LoadSimulator loadSimulator;


    @Override
    public void run(String... args) throws Exception {
        String host = Constants.DEFAULT_HOST;
        String queue = Constants.DEFAULT_QUEUE_NAME;

        try {

            LoadSimulatorConsumer consumer = new LoadSimulatorConsumer();
            consumer.connect(host, queue);

            SimulatorMessage message;
            while (true) {
                message = consumer.retrieveSimulatorMessage();
                if (message != null) {
                    log.info(String.format("Retrieved simulator message %s", message));

                    loadSimulator.setUpSimulation(message);
                    loadSimulator.runSimulation();

                } else {
                    log.info("No message available, sleeping...");
                    Thread.sleep(5000);
                }
            }
        } catch (InterruptedException | IOException | TimeoutException e) {
            log.warn("Caught Exception: " + e.getMessage());
//            e.printStackTrace();
        }
    }

}
