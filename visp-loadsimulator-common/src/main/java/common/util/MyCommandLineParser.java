package common.util;

import org.apache.commons.cli.*;

import static common.util.Constants.DEFAULT_HOST;
import static common.util.Constants.DEFAULT_QUEUE_NAME;

/**
 * Created by martensigwart on 23.05.17.
 */
public class MyCommandLineParser {

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
            queue = DEFAULT_QUEUE_NAME;
        }
        return queue;
    }


    private void parse() throws ParseException {
        if (cmd == null) {
            cmd = parser.parse(options, args);
        }
    }

}
