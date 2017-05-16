package common;

import java.io.*;

/**
 * Created by martensigwart on 03.05.17.
 */
public class LoadSimulatorMessage implements Serializable {

    private static final long serialVersionUID = 2698486498784998275L;


    private SimulationType type;    // what type of simulation to perform -> RAM, CPU, etc.
    private Integer workload;       // in percent
    private Integer duration;       // in seconds
    private Integer method;         // what method to use for simulation


    public LoadSimulatorMessage(SimulationType type, Integer workload, Integer duration, Integer method) {
        this.type = type;
        this.workload = workload;
        this.duration = duration;
        this.method = method;
    }

    public LoadSimulatorMessage(SimulationType type, Integer workload, Integer duration) {
        this(type, workload, duration, Constants.DEFAULT_METHOD);
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

    public java.lang.Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public byte[] getBytes() {
        byte[] bytes;
        ByteArrayOutputStream baos;
        ObjectOutputStream oos;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.flush();
            oos.reset();
            bytes = baos.toByteArray();
            oos.close();
            baos.close();

        } catch (IOException e) {
            bytes = new byte[] {};
            System.out.println("Unable to write to output stream: " + e.getMessage());

        }
        return bytes;
    }

    public static LoadSimulatorMessage fromBytes(byte[] bytes) {
        LoadSimulatorMessage msg = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            msg = (LoadSimulatorMessage) ois.readObject();
            ois.close();
            bis.close();

        } catch (IOException e) {
            System.out.println("Unable to read from output stream: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());

        }
        return msg;
    }

        @Override
    public String toString() {
        return "LoadSimulatorMessage{" +
                "type=" + type +
                ", workload=" + workload +
                ", duration=" + duration +
                ", method=" + method +
                '}';
    }


}
