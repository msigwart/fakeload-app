package common;

import java.io.Serializable;

/**
 * Created by martensigwart on 16.05.17.
 */
public class SimulatorMessagePart implements Serializable {

    private static final long serialVersionUID = 8779625952217937753L;


    private SimulationType type;    // what type of simulation to perform -> RAM, CPU, etc.
    private Integer workload;       // workload in percent
    private Integer method;         // what simulation method to use

    public SimulatorMessagePart(SimulationType type, Integer workload, Integer method) {
        this.type = type;
        this.workload = workload;
        this.method = method;
    }

    public SimulatorMessagePart(SimulationType type, Integer workload) {
        this(type, workload, Constants.DEFAULT_METHOD);
    }

    public SimulationType getType() {
        return type;
    }

    public void setType(SimulationType type) {
        this.type = type;
    }

    public Integer getWorkload() {
        return workload;
    }

    public void setWorkload(Integer workload) {
        this.workload = workload;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "SimulatorMessagePart{" +
                "type=" + type +
                ", workload=" + workload +
                ", method=" + method +
                '}';
    }
}
