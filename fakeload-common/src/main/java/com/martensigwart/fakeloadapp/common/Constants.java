package com.martensigwart.fakeloadapp.common;

import com.martensigwart.fakeloadapp.common.SimulationType;


public class Constants {

    public final static String DEFAULT_QUEUE_NAME = "simulator-queue";
    public final static String DEFAULT_HOST = "localhost";

    public static final int DURATION_MAX = 3600;


    /* Default Values */
    public static final long            DEFAULT_CPU_WORKLOAD = 50;
    public static final long            DEFAULT_RAM_WORKLOAD = 50;
    public static final int             DEFAULT_DURATION = 10;
    public static final int             DEFAULT_METHOD = 0;
    public static final SimulationType DEFAULT_SIMULATION_TYPE = SimulationType.CPU;

}
