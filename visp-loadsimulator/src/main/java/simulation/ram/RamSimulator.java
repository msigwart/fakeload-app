package simulation.ram;

/**
 * Created by martensigwart on 17.05.17.
 */
public class RamSimulator implements IRamSimulator {

    private Integer workload;

    public RamSimulator(Integer workload) {
        this.workload = workload;
    }

    @Override
    public void allocateMemory() {

    }
}
