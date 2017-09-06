package com.martensigwart.fakeloadapp.client;

import com.martensigwart.fakeload.FakeLoad;
import com.martensigwart.fakeload.FakeLoads;
import com.martensigwart.fakeload.MemoryUnit;
import com.martensigwart.fakeloadapp.common.FakeLoadMessage;
import com.martensigwart.fakeloadapp.common.MyCommandLineParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;



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

                    FakeLoad fakeload = createFakeLoad(br, false);


                    // send message?
                    System.out.printf("Send message: %s? [y/n]:", fakeload);
                    String input = br.readLine();
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

    private static FakeLoad createFakeLoad(BufferedReader br, boolean isChild) throws IOException {
        FakeLoad fakeload = FakeLoads.create();

        fakeload = promptDuration(fakeload, br);

        fakeload = promptCpu(fakeload, br);

        fakeload = promptMemory(fakeload, br);

        fakeload = promptDiskInput(fakeload, br);

        fakeload = promptDiskOutput(fakeload, br);

        fakeload = promptOptions(fakeload, br, isChild);

        return fakeload;
    }

    private static FakeLoad promptOptions(FakeLoad fakeload, BufferedReader br, boolean isChild) throws IOException {
        while (true) {
            try {
                System.out.println("What now?");
                System.out.println(" [1] - Set duration");
                System.out.println(" [2] - Set CPU");
                System.out.println(" [3] - Set Memory");
                System.out.println(" [4] - Set Disk Input");
                System.out.println(" [5] - Set Disk Output");
                System.out.println(" [6] - Add inner FakeLoad");
                System.out.println("-----------------------");
                System.out.println(" [0] - " + (isChild ? "Add to parent" : "I'm done"));
                System.out.println("[99] - Show FakeLoad");
                System.out.printf("Enter your choice (default [0]):");
                String input = br.readLine();
                int choice = (int) parseLong(input);

                switch (choice) {
                    case 0:
                        return fakeload;
                    case 1:
                        fakeload = promptDuration(fakeload, br);
                        break;
                    case 2:
                        fakeload = promptCpu(fakeload, br);
                        break;
                    case 3:
                        fakeload = promptMemory(fakeload, br);
                        break;
                    case 4:
                        fakeload = promptDiskInput(fakeload, br);
                        break;
                    case 5:
                        fakeload = promptDiskOutput(fakeload, br);
                        break;
                    case 6:
                        fakeload = fakeload.addLoad(createFakeLoad(br, true));
                        break;
                    case 99:
                        System.out.println(fakeload);
                        break;
                    default:
                        System.out.println("Illegal option");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static FakeLoad promptDuration(FakeLoad fakeload, BufferedReader br) throws IOException {
        // Get duration
        System.out.printf("Enter duration (in seconds):");
        String input = br.readLine();
        long duration = parseLong(input);
        return fakeload.lasting(duration, TimeUnit.SECONDS);
    }

    private static FakeLoad promptCpu(FakeLoad fakeload, BufferedReader br) throws IOException {
        while (true) {
            try {
                // Get CPU load
                System.out.printf("Enter CPU load (in percent):");
                String input = br.readLine();
                int cpuLoad = (int) parseLong(input);
                return fakeload.withCpu(cpuLoad);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static FakeLoad promptMemory(FakeLoad fakeload, BufferedReader br) throws IOException {
        while (true) {
            try {
                // Get Memory load
                System.out.printf("Enter memory load (in bytes):");
                String input = br.readLine();
                long memoryLoad = parseLong(input);
                return fakeload.withMemory(memoryLoad, MemoryUnit.BYTES);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static FakeLoad promptDiskInput(FakeLoad fakeload, BufferedReader br) throws IOException {
        while (true) {
            try {
                // Get DiskInput load
                System.out.printf("Enter disk input load (in bytes per seconds):");
                String input = br.readLine();
                long diskInputLoad = parseLong(input);
                return fakeload.withDiskInput(diskInputLoad, MemoryUnit.BYTES);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static FakeLoad promptDiskOutput(FakeLoad fakeload, BufferedReader br) throws IOException {
        while (true) {
            try {
                // Get Disk Output load
                System.out.printf("Enter disk output load (in bytes per seconds):");
                String input = br.readLine();
                long diskOutputLoad = parseLong(input);
                return fakeload.withDiskOutput(diskOutputLoad, MemoryUnit.BYTES);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static long parseLong(String input) {
        if (input.equals("")) {
            return 0;
        }
        long number;
        try {
            number = Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Input has to be a number"));
        }
        return number;
    }





}
