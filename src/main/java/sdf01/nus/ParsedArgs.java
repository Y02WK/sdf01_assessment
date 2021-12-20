package sdf01.nus;

public class ParsedArgs {
    // format arguments from command line and stores them

    // data members with default values
    private String[] docRoot = new String[] { "static" };
    private int port = 3000;

    public ParsedArgs(String[] args) {
        this.parseArgs(args);
    }

    public String[] getDocRoot() {
        return this.docRoot;
    }

    public int getPort() {
        return this.port;
    }

    private void parseArgs(String[] args) {
        // base case
        if (args.length == 0) {
            return;
        }

        // iteration to check for args. (no validation)
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("--docRoot")) {
                docRoot = args[i + 1].split(":");
            }
        }
    }
}
