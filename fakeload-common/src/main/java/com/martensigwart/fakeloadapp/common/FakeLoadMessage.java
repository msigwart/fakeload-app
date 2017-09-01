package com.martensigwart.fakeloadapp.common;

import com.martensigwart.fakeload.FakeLoad;

import java.io.*;


public final class FakeLoadMessage implements Serializable {

    private static final long serialVersionUID = 2698486498784998275L;

    private final FakeLoad fakeLoad;


    public FakeLoadMessage(FakeLoad fakeLoad) {
        this.fakeLoad = fakeLoad;
    }

    public FakeLoad getFakeLoad() {
        return fakeLoad;
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

    public static FakeLoadMessage fromBytes(byte[] bytes) {
        FakeLoadMessage msg = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            msg = (FakeLoadMessage) ois.readObject();
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
        return "FakeLoadMessage{" +
                "fakeLoad=" + fakeLoad +
                '}';
    }
}
