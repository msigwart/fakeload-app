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

    Long load10 = 10L;
    Long load20 = 20L;
    Long load30 = 30L;
    Long load40 = 40L;
    Long load50 = 50L;
    Long load60 = 60L;
    Long load70 = 70L;
    Long load80 = 80L;
    Long load90 = 90L;
    Long load100 = 100L;

    Long step10loads[] = {load10, load20, load30, load40, load50, load60, load70, load80, load90, load100};



    static LoadSimulatorProducer producer;

    public static void initialize() throws IOException, TimeoutException {
        producer = new LoadSimulatorProducer();
        producer.connect(HOST, QUEUE);
    }

}
