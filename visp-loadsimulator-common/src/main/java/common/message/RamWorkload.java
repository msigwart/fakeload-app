package common.message;

import common.enums.WorkloadUnit;
import common.util.Constants;

/**
 * Created by martensigwart on 23.06.17.
 */
public class RamWorkload extends AbstractWorkload {

    public static String promptMessage() {
        return "(specify k, m, g, or percent, eg. 1024m or 50%):";
    }


    public RamWorkload(String input) {

        // Parse input for valid RAM workload
        if (input.equals("")) {
            unit = WorkloadUnit.PERCENT;
            value = Constants.DEFAULT_RAM_WORKLOAD;
            return;
        }

        try {

            int splitIndex;

            // value as percent
            if ((splitIndex = input.indexOf("%".toString())) >= 0) {
                unit = WorkloadUnit.PERCENT;
                input = input.substring(0, splitIndex);
                value = Integer.parseInt(input);
                if (value < 1 || value > 100) {
                    throw new IllegalArgumentException("Workload has to be between 0 and 100 %");
                }
                return;
            }

            // value as kB
            if ((splitIndex = input.indexOf("k".toString())) >= 0) {
                unit = WorkloadUnit.KB;
                input = input.substring(0, splitIndex);
                value = Integer.parseInt(input);
                assertPositive(value);
                return;
            }

            // value as MB
            if ((splitIndex = input.indexOf("m".toString())) >= 0) {
                unit = WorkloadUnit.MB;
                input = input.substring(0, splitIndex);
                value = Integer.parseInt(input);
                assertPositive(value);
                return;
            }

            // value as GB
            if ((splitIndex = input.indexOf("g".toString())) >= 0) {
                unit = WorkloadUnit.GB;
                input = input.substring(0, splitIndex);
                value = Integer.parseInt(input);
                assertPositive(value);
                return;
            }

            // value as byte
            unit = WorkloadUnit.BYTE;
            value = Integer.parseInt(input);
            assertPositive(value);
            return;


        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }

    }

    private void assertPositive(Integer value) throws IllegalArgumentException {
        if (value <= 0) throw new IllegalArgumentException("Value must be bigger than 0");
    }
}
