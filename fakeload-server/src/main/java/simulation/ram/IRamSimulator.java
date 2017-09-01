package simulation.ram;

import java.util.concurrent.Callable;

/**
 * Created by martensigwart on 17.05.17.
 */
public interface IRamSimulator extends Callable<String> {
    void allocateMemory();
}
