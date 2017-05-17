package simulation;

import common.SimulatorMessage;
import common.consumer.LoadSimulatorConsumer;
import common.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by martensigwart on 16.05.17.
 */
@Component
public class LoadSimulator implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        String host = Constants.DEFAULT_HOST;
        String queue = Constants.DEFAULT_QUEUE_NAME;

        try {

            LoadSimulatorConsumer consumer = new LoadSimulatorConsumer();
            consumer.connect(host, queue);

            while (true) {
                if (consumer == null) {
                    System.out.println("This is strange");
                }
                SimulatorMessage message = consumer.retrieveSimulatorMessage();
                if (message != null) {
                    System.out.printf("Retrieved simulator message %s\n", message);

                } else {
                    System.out.printf("No message available, sleeping...\n");
                    Thread.sleep(5000);
                }
            }
        } catch (InterruptedException | IOException | TimeoutException e) {
            System.out.println("Encountered a problem");
            e.printStackTrace();
        }
    }


}
