package simulation;

import common.enums.SimulationType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static common.enums.SimulationType.*;

/**
 * Created by martensigwart on 13.06.17.
 */
public class SimulationLoad {

    private Map<SimulationType, Integer> workloads = new ConcurrentHashMap<>();


    public Integer getCpuLoad() {
        return workloads.get(CPU);
    }

    public void setCpuLoad(Integer cpuLoad) {
        workloads.put(CPU, cpuLoad);
    }

    public Integer getRamLoad() {
        return workloads.get(RAM);
    }

    public void adjustCpuLoadBy(Integer addedLoad) {
        Integer oldWorkload = getCpuLoad();
        setCpuLoad(Math.min(oldWorkload + addedLoad, 100));
    }

    public Map<SimulationType, Integer> getWorkloads() {
        return workloads;
    }
}
