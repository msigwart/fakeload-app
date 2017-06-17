import common.SimulatorMessage;
import common.SimulatorMessagePart;
import common.enums.SimulationType;
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
    public void single1minSimulations() {

        Integer durations[] = {1}; // 10min

        sendMessages(durations, step10loads, 1);

    }


    @Test
    public void single2minSimulations() {

        Integer durations[] = {2};
        sendMessages(durations, step10loads, 1);

    }


    @Test
    public void longCpuTest() {

        Integer durations[] = {1, 2, 3, 5, 10, 20}; // 410min ~ 7h
        sendMessages(durations, step10loads, 1);

    }


    @Test
    public void repeated10minSimulations() {
        Integer durations[] = {10};
        sendMessages(durations, step10loads, 5);
    }



    @Test
    public void repeated20minSimulations() {
        Integer durations[] = {10};
        sendMessages(durations, step10loads, 5);
    }


    @Test
    public void customSimulations() {
        Integer durations[] = {5, 10, 15};
        Integer loads[] = {20, 40, 60, 80, 100};
        Integer repetititons = 3;

        sendMessages(durations, loads, repetititons);
    }


    private void sendMessages(Integer[] durations, Integer[] loads, Integer noOfRepetitions) {
        for (Integer time: durations) {
            for (Integer load: loads) {

                for (int i=0; i<noOfRepetitions; i++) {
                    Map<SimulationType, SimulatorMessagePart> map = new HashMap<>();
                    map.put(CPU, new SimulatorMessagePart(CPU, load));
                    SimulatorMessage m = new SimulatorMessage(time * 60, map);
                    try {
                        producer.sendLoadSimulationMessage(m);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
