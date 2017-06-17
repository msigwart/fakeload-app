package simulation;

import common.enums.SimulationType;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by martensigwart on 13.06.17.
 */
public class LoadControlObject {

    private final SimulationType type;

    // Adjustment Variables
    private final Integer initialWorkload;                                   // The inital workload --> cannot be modified
    private final Integer method;                                            // The simulation method
    private AtomicInteger noPermits = new AtomicInteger(0);                  // Integer indicating number of adjustment steps
    private volatile AdjustmentType adjustmentType;                          // INCREASE or DECREASE



    public LoadControlObject(SimulationType type, Integer initialWorkload, Integer method) {

        this.type = type;
        this.initialWorkload = initialWorkload;
        this.method = method;
    }


    public void setAdjustment(Integer noOfAdjustments, AdjustmentType type) {
        this.noPermits.set(noOfAdjustments);
        this.adjustmentType = type;
    }

    public Integer getAndDecrementPermits() {
        return noPermits.getAndDecrement();
    }


    public Integer getInitialWorkload() {
        return initialWorkload;
    }

    public Integer getMethod() {
        return method;
    }

    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

//
//    public void adjustCpuLoadBy(Integer addedLoad) {
//        Integer oldWorkload = getCpuLoad();
//        setCpuLoad(Math.min(oldWorkload + addedLoad, 100));
//    }


    public enum AdjustmentType {
        INCREASE, DECREASE
    }
}
