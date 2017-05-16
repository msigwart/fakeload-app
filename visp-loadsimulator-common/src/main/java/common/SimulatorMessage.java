package common;

import common.enums.SimulationType;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by martensigwart on 03.05.17.
 */
public class SimulatorMessage implements Serializable {

    private static final long serialVersionUID = 2698486498784998275L;

    private Integer duration;                                  // duration of simulation in seconds
    private Map<SimulationType, SimulatorMessagePart> parts;   // different simulator parts


    public SimulatorMessage() {
        this.parts = new HashMap<SimulationType, SimulatorMessagePart>();
    }

    public SimulatorMessage(Integer duration) {
        this.duration = duration;
        this.parts = new HashMap<SimulationType, SimulatorMessagePart>();
    }

    public SimulatorMessage(Integer duration, Map<SimulationType, SimulatorMessagePart> parts) {
        this(duration);
        this.parts = parts;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Map<SimulationType, SimulatorMessagePart> getParts() {
        return parts;
    }

    public void setParts(Map<SimulationType, SimulatorMessagePart> parts) {
        this.parts = parts;
    }

    public void addPart(SimulatorMessagePart part) {
        this.parts.put(part.getType(), part);
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

    public static SimulatorMessage fromBytes(byte[] bytes) {
        SimulatorMessage msg = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            msg = (SimulatorMessage) ois.readObject();
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
        return "SimulatorMessage{" +
                "duration=" + duration +
                ", parts=" + parts +
                '}';
    }
}
