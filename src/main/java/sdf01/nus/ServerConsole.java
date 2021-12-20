package sdf01.nus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerConsole {
    private ExecutorService threadPool;
    private Future<?> serverFuture;
    private boolean serverRunning = false;
    HttpServer serverInstance;

    public ServerConsole(int port, String[] docRoot) {
        // single thread executor for one instance of the server
        this.serverInstance = new HttpServer(port, docRoot);
        threadPool = Executors.newSingleThreadExecutor();
    }

    // run server console
    public void run() {
        System.out.println("Enter 'start' to start the server, or 'quit' to exit");
        while (true) {
            String cmd = "";
            try {
                cmd = getUserInput();
            } catch (IOException e) {
                System.err.println("Error getting user input.");
            }
            if (!processInput(cmd)) {
                System.exit(0);
            }
        }
    }

    // get input from console
    private String getUserInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    // process user input from console
    private boolean processInput(String cmd) {
        boolean result = switch (cmd.strip().toLowerCase()) {
            case "start":
                runServer();
                yield true;
            case "stop":
                stopServer();
                yield true;
            case "quit":
                quit();
                yield false;
            default:
                System.out.println("Command not recognized. 'start'/'stop'/'quit' only");
                yield true;
        };

        return result;
    }

    // init server instance
    private void runServer() {
        serverFuture = threadPool.submit(serverInstance);
        serverRunning = true;
        System.out.println("Server started. Enter 'stop' to stop server.");
    }

    // stops the server instance immediately if server is running
    private void stopServer() {
        if (serverRunning) {
            serverFuture.cancel(true);
            serverRunning = false;
            System.out.println("Server stopped");
        } else {
            System.out.println("Server not started. Enter 'start' to start server.");
        }
    }

    // quit and shutdown the program
    private void quit() {
        stopServer();
        threadPool.shutdown();
        System.out.println("Quit. Byebye");
    }
}