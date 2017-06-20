import client.producer.LoadSimulatorProducer;
import common.util.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by martensigwart on 16.06.17.
 */
public abstract class AbstractTestBase {

    public static final String HOST="128.130.172.178";
    public static final String QUEUE= Constants.DEFAULT_QUEUE_NAME;

    Integer load10 = 10;
    Integer load20 = 20;
    Integer load30 = 30;
    Integer load40 = 40;
    Integer load50 = 50;
    Integer load60 = 60;
    Integer load70 = 70;
    Integer load80 = 80;
    Integer load90 = 90;
    Integer load100 = 100;

    Integer step10loads[] = {load10, load20, load30, load40, load50, load60, load70, load80, load90, load100};



    static LoadSimulatorProducer producer;

    public static void initialize() throws IOException, TimeoutException {
        producer = new LoadSimulatorProducer();
        producer.connect(HOST, QUEUE);
    }

}
