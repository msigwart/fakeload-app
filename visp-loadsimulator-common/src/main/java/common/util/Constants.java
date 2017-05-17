package common.util;

import common.enums.SimulationType;

/**
 * Created by martensigwart on 03.05.17.
 */
public class Constants {

    public final static String DEFAULT_QUEUE_NAME = "simulator-queue";
    public final static String DEFAULT_HOST = "localhost";

    public static final int DURATION_MAX = 1800;


    /* Default Values */
    public static final int             DEFAULT_WORKLOAD = 50;
    public static final int             DEFAULT_DURATION = 10;
    public static final int             DEFAULT_METHOD = 0;
    public static final SimulationType  DEFAULT_SIMULATION_TYPE = SimulationType.CPU;

}
