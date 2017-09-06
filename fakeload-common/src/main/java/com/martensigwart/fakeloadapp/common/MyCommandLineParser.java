package com.martensigwart.fakeloadapp.common;

import org.apache.commons.cli.*;

public class MyCommandLineParser {

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_QUEUE = "fakeload-queue";
    private CommandLineParser parser;
    private Options options;
    private CommandLine cmd;
    private String[] args;

    public MyCommandLineParser(String[] args) throws ParseException {
        // Create Parser
        parser = new DefaultParser();

        // Create Options
        options = new Options();
        options.addOption("h", "host", true, "host of rabbitmq server");
        options.addOption("q", "queue", true, "queue name");

        // Save arguments
        this.args = args;
    }

    private void parse() throws ParseException {
        if (cmd == null) {
            cmd = parser.parse(options, args);
        }
    }


    public String parseHost() throws ParseException {
        parse();

        // Parse host
        String host;
        if (cmd.hasOption("h")) {
            host = cmd.getOptionValue("h");
        } else if (cmd.hasOption("host")) {
            host = (String)cmd.getParsedOptionValue("host");
        } else {
            host = DEFAULT_HOST;
        }
        //TODO check if valid host

        return host;
    }


    public String parseQueue() throws ParseException {
        parse();

        // Parse queue name
        String queue;
        if (cmd.hasOption("q")) {
            queue = cmd.getOptionValue("q");
        } else if (cmd.hasOption("queue")) {
            queue = (String)cmd.getParsedOptionValue("queue");
        } else {
            queue = DEFAULT_QUEUE;
        }
        return queue;
    }

}
