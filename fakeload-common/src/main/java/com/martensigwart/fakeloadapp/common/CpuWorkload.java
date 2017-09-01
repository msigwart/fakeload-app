package com.martensigwart.fakeloadapp.common;

/**
 * Created by martensigwart on 23.06.17.
 */
public class CpuWorkload extends AbstractWorkload {

    public static String promptMessage() {
        return "(in %):";
    }


    public CpuWorkload(Long workload) {
        this(workload.toString());
    }


    public CpuWorkload(String input) {
        unit = WorkloadUnit.PERCENT;

        // parse input for valid CPU workload
        if (input.equals("")) {
            value = Constants.DEFAULT_CPU_WORKLOAD;
            return;
        }

        Long workload;
        try {
            workload = Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }

        if (workload < 1 || workload > 100) {
            throw new IllegalArgumentException("Workload has to be between 0 and 100 %");
        }

        value = workload;

    }

}
