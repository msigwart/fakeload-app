package common.message;

import common.enums.WorkloadUnit;

import java.io.Serializable;

/**
 * Created by martensigwart on 23.06.17.
 */
public interface IWorkload extends Serializable {
    Long getValue();
    WorkloadUnit getUnit();
}
