import common.SimulatorMessage;
import common.SimulatorMessagePart;
import common.enums.SimulationType;
import common.producer.LoadSimulatorProducer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static common.enums.SimulationType.CPU;

/**
 * Created by martensigwart on 16.06.17.
 */
public class CPULoadTest extends AbstractTestBase {


    @BeforeClass
    public static void setUpClass() {
        try {
            initialize();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Test
    public void superShortCpuTest() {

        Integer durations[] = {1}; // 10min
        sendMessages(durations);

    }


//    @Test
    public void shortCpuTest() {

        Integer durations[] = {1, 2, 3}; // 60min = 1h
        sendMessages(durations);

    }


//    @Test
    public void longCpuTest() {

        Integer durations[] = {1, 2, 3, 5, 10, 20}; // 410min ~ 7h
        sendMessages(durations);

    }


    private void sendMessages(Integer[] durations) {
        for (Integer time: durations) {
            for (SimulatorMessagePart part: allParts) {

                Map<SimulationType, SimulatorMessagePart> map = new HashMap<>();
                map.put(CPU, part);
                SimulatorMessage m = new SimulatorMessage(time*60, map);
                try {
                    producer.sendLoadSimulationMessage(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
