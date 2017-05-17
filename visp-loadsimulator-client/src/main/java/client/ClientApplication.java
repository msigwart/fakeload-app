package client;

import common.*;
import common.enums.CpuSimulationMethod;
import common.enums.RamSimulationMethod;
import common.enums.SimulationType;
import common.producer.LoadSimulatorProducer;
import common.util.Constants;
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static common.util.Constants.DEFAULT_METHOD;
import static common.util.Constants.DURATION_MAX;


/**
 * Created by martensigwart on 02.05.17.
 */
public class ClientApplication {


    public static void main(String[] args) {

        String host = Constants.DEFAULT_HOST;
        String queue = Constants.DEFAULT_QUEUE_NAME;

        // Create parser
        CommandLineParser parser = new DefaultParser();

        // Create options
        Options options = new Options();
        options.addOption("h", "host", true, "host of rabbitmq server");
        options.addOption("q", "queue", true, "queue name");

        try {
            // Parse command line arguments
            CommandLine cmd = parser.parse(options, args);

            // Parse host
            if (cmd.hasOption("h")) {
                host = cmd.getOptionValue("h");
            } else if (cmd.hasOption("host")) {
                host = (String)cmd.getParsedOptionValue("host");
            } //TODO check if valid host

            // Parse queue name
            if (cmd.hasOption("q")) {
                queue = cmd.getOptionValue("q");
            } else if (cmd.hasOption("queue")) {
                queue = (String)cmd.getParsedOptionValue("queue");
            }

            // Create new connection
            LoadSimulatorProducer client = new LoadSimulatorProducer();
            client.connect(host, queue);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {

                try {
                    System.out.printf("++ NEW SIMULATOR MESSAGE ++\n");
                    SimulatorMessage message = new SimulatorMessage();
                    boolean messageDone = false;

                    // get duration
                    System.out.printf("Enter duration (in seconds):");
                    String input = br.readLine();
                    Integer duration = getValidDuration(input);
                    message.setDuration(duration);

                    while (!messageDone) {

                        if (message.getParts().size() != 0) {
                            System.out.printf("Are you done? [y/n]:");
                            input = br.readLine();
                            if (input.equals("y")) {
                                messageDone = true;
                                continue;
                            }
                        }

                        SimulationType type;
                        Integer workload, method;

                        // get type
                        promptSimulationType();
                        input = br.readLine();
                        type = getValidType(input);

                        // get workload
                        System.out.print("Enter workload (in %):");
                        input = br.readLine();
                        workload = getValidWorkload(input);

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
                    if (!input.equals("y")) {
                        continue;
                    }

                    client.sendLoadSimulationMessage(message);
                    System.out.printf("Successfully sent message\n");


                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }

        } catch (Exception e) {
            System.out.printf("Unexpected exception: %s %s\n", e.getClass().getSimpleName(), e.getMessage());
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


    private static Integer getValidWorkload(String input) {
        Integer workload;
        try {
            workload = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }

        if (workload < 1 || workload > 100) {
            throw new IllegalArgumentException("Workload has to be between 0 and 100 %");
        }
        return workload;
    }


    private static Integer getValidDuration(String input) {
        Integer duration;
        try {
            duration = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }
        if (duration < 1 || duration > DURATION_MAX) {
            throw new IllegalArgumentException("Duration has to be between 0 and " + DURATION_MAX);
        }

        return duration;
    }


    private static Integer getValidSimulationMethod(SimulationType type, String input) {
        Integer method;
        try {

            if (!input.equals("")) {
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

            } else {
                method = DEFAULT_METHOD;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }
        return method;
    }


}