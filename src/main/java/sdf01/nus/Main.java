package sdf01.nus;

public class Main {
    public static void main(String[] args) {
        int port;
        String[] docRoot;

        // parse args
        ParsedArgs parsedArgs = new ParsedArgs(args);

        port = parsedArgs.getPort();
        docRoot = parsedArgs.getDocRoot();

        // init server instance
        // HttpServer server = new HttpServer(port, docRoot);
        // server.run();

        // init server console
        ServerConsole console = new ServerConsole(port, docRoot);
        console.run();
    }
}