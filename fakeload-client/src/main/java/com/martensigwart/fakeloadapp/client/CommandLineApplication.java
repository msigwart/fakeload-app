package com.martensigwart.fakeloadapp.client;

import com.martensigwart.fakeload.FakeLoad;
import com.martensigwart.fakeload.FakeLoads;
import com.martensigwart.fakeloadapp.common.*;
import com.martensigwart.fakeloadapp.common.CpuSimulationMethod;
import com.martensigwart.fakeloadapp.common.RamSimulationMethod;
import com.martensigwart.fakeloadapp.common.SimulationType;
import com.martensigwart.fakeloadapp.common.Constants;
import com.martensigwart.fakeloadapp.common.MyCommandLineParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.martensigwart.fakeloadapp.common.Constants.DEFAULT_METHOD;
import static com.martensigwart.fakeloadapp.common.Constants.DURATION_MAX;


public class CommandLineApplication {


    public static void main(String[] args) {

        try {

            // Parse command line arguments
            MyCommandLineParser parser = new MyCommandLineParser(args);
            String host = parser.parseHost();
            String queue = parser.parseQueue();

            // Create new connection
            FakeLoadProducer client = new FakeLoadProducer();
            client.connect(host, queue);

            System.out.printf("+++ Connected to RabbitMQ at %s, queue: %s +++\n", host, queue);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {

                try {
                    System.out.printf("+ New FakeLoad Message +\n");
                    FakeLoad fakeload = FakeLoads.create();
                    boolean messageDone = false;

                    // get duration
                    System.out.printf("Enter duration (in seconds):");
                    String input = br.readLine();
                    long duration = parseDuration(input);
                    message.setDuration(duration);

                    while (!messageDone) {

                        if (message.getParts().size() != 0) {
                            System.out.printf("Are you done? [y/n]:");
                            input = br.readLine();
                            if (input.equals("y") || input.equals("")) {
                                messageDone = true;
                                continue;
                            }
                        }

                        SimulationType type;
                        IWorkload workload;
                        Integer method;

                        // get type
                        promptSimulationType();
                        input = br.readLine();
                        type = getValidType(input);

                        // get workload
                        promptSimulationWorkload(type);
                        input = br.readLine();
                        workload = getValidWorkload(type, input);

                        // get method
                        promptSimulationMethod(type);
                        input = br.readLine();
                        method = getValidSimulationMethod(type, input);

                        SimulatorMessagePart messagePart = new SimulatorMessagePart(type, workload, method);

                        if (message.getParts().containsKey(type)) {
                            System.out.printf("Message already contains a part of type %s. Overwrite? [y/n]:", type);
                            input = br.readLine();
                            if (!input.equals("y")) {
                                continue;
                            }
                        }
                        message.addPart(messagePart);
                        System.out.printf("Added part %s to message\n", messagePart);

                    }

                    // send message?
                    System.out.printf("Send message: %s? [y/n]:", message);
                    input = br.readLine();
                    if (!input.equals("y") && !input.equals("") ) {
                        continue;
                    }

                    FakeLoadMessage message = new FakeLoadMessage(fakeload);
                    client.sendFakeLoadMessage(message);
                    System.out.printf("Successfully sent message\n");


                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }

        } catch (Exception e) {
            System.out.printf("Unexpected exception: %s %s\n", e.getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

    }



    private static void promptSimulationType() {
        System.out.printf("Enter Simulation Type (");
        for (int i=0; i<SimulationType.values().length; i++) {
            System.out.printf("%s=%d", SimulationType.values()[i], i);
            if (i<SimulationType.values().length-1) {
                System.out.printf(", ");
            }
        }
        System.out.printf("):");
    }

    private static void promptSimulationWorkload(SimulationType type) {
        System.out.print("Enter workload ");

        if (type == SimulationType.CPU) {
            System.out.print(CpuWorkload.promptMessage());

        } else if (type == SimulationType.RAM) {
            System.out.print(RamWorkload.promptMessage());
        }
    }

    private static void promptSimulationMethod(SimulationType type) {
        System.out.print("Enter simulation method (");
        switch (type) {
            case CPU:
                for (int i = 0; i< CpuSimulationMethod.values().length; i++) {
                    System.out.printf("%s=%d", CpuSimulationMethod.values()[i], i);
                    if (i<CpuSimulationMethod.values().length-1) {
                        System.out.printf(", ");
                    }
                }
                break;
            case RAM:
                for (int i = 0; i< RamSimulationMethod.values().length; i++) {
                    System.out.printf("%s=%d", RamSimulationMethod.values()[i], i);
                    if (i<RamSimulationMethod.values().length-1) {
                        System.out.printf(", ");
                    }
                }
                break;
            default:
                break;
        }

        System.out.printf("):");
    }


    private static SimulationType getValidType(String input) throws IllegalArgumentException {

        if (input.equals("")) {
            return Constants.DEFAULT_SIMULATION_TYPE;
        }
        Integer type;
        try {
            type = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a valid type number"));
        }

        for (SimulationType t: SimulationType.values()) {
            if (type.equals(t.ordinal())) {
                return t;
            }
        }
        throw new IllegalArgumentException(type + " is not a valid type number");
    }


    private static IWorkload getValidWorkload(SimulationType type, String input) {
        switch (type) {
            case CPU:
                return new CpuWorkload(input);
            case RAM:
                return new RamWorkload(input);
        }
        throw new IllegalArgumentException("Not able to parse a valid load");
    }



    private static long parseDuration(String input) {
        if (input.equals("")) {
            return Constants.DEFAULT_DURATION;
        }
        long duration;
        try {
            duration = Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }
        if (duration < 1 || duration > DURATION_MAX) {
            throw new IllegalArgumentException("Duration has to be between 0 and " + DURATION_MAX);
        }

        return duration;
    }


    private static Integer getValidSimulationMethod(SimulationType type, String input) {
        if (input.equals("")) {
            return Constants.DEFAULT_METHOD;
        }
        Integer method;
        try {
            method = Integer.parseInt(input);
            switch (type) {
                case CPU:
                    method = CpuSimulationMethod.values()[method].ordinal();
                    break;
                case RAM:
                    method = RamSimulationMethod.values()[method].ordinal();
                    break;
                default:
                    method = DEFAULT_METHOD;
                    break;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }
        return method;
    }


}
