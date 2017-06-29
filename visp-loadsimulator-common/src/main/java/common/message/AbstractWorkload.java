package common.message;

import common.enums.WorkloadUnit;

/**
 * Created by martensigwart on 23.06.17.
 */
public abstract class AbstractWorkload implements IWorkload {

    private static final long serialVersionUID = 9088205519364876750L;

    protected Long value;
    protected WorkloadUnit unit;

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public WorkloadUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return  this.getClass().getSimpleName() + "{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}
