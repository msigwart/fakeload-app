package simulation.cpu;

import java.util.concurrent.Callable;

/**
 * Created by martensigwart on 17.05.17.
 */
public interface ICpuSimulator extends Callable<String> {
    void simulateCpu();
}
