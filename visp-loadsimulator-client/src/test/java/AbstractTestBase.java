import common.SimulatorMessagePart;
import common.enums.SimulationType;
import common.producer.LoadSimulatorProducer;
import common.util.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by martensigwart on 16.06.17.
 */
public abstract class AbstractTestBase {

    public static final String HOST="128.130.172.178";
    public static final String QUEUE= Constants.DEFAULT_QUEUE_NAME;

    SimulatorMessagePart cpu10 = new SimulatorMessagePart(SimulationType.CPU, 10);
    SimulatorMessagePart cpu20 = new SimulatorMessagePart(SimulationType.CPU, 20);
    SimulatorMessagePart cpu30 = new SimulatorMessagePart(SimulationType.CPU, 30);
    SimulatorMessagePart cpu40 = new SimulatorMessagePart(SimulationType.CPU, 40);
    SimulatorMessagePart cpu50 = new SimulatorMessagePart(SimulationType.CPU, 50);
    SimulatorMessagePart cpu60 = new SimulatorMessagePart(SimulationType.CPU, 60);
    SimulatorMessagePart cpu70 = new SimulatorMessagePart(SimulationType.CPU, 70);
    SimulatorMessagePart cpu80 = new SimulatorMessagePart(SimulationType.CPU, 80);
    SimulatorMessagePart cpu90 = new SimulatorMessagePart(SimulationType.CPU, 90);
    SimulatorMessagePart cpu100 = new SimulatorMessagePart(SimulationType.CPU, 100);

    SimulatorMessagePart allParts[] = {cpu10, cpu20, cpu30, cpu40, cpu50, cpu60, cpu70, cpu80, cpu90, cpu100};



    static LoadSimulatorProducer producer;

    public static void initialize() throws IOException, TimeoutException {
        producer = new LoadSimulatorProducer();
        producer.connect(HOST, QUEUE);
    }

}
