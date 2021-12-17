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
    private HttpServer serverInstance;
    private boolean serverRunning = false;

    public ServerConsole(int port, String[] docRoot) {
        // single thread executor for one instance of the server
        threadPool = Executors.newSingleThreadExecutor();
        this.serverInstance = new HttpServer(port, docRoot);
    }

    // run server console
    public void run() {
        System.out.println("Enter 'start' to start the server.");
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

    private boolean processInput(String cmd) {
        switch (cmd.trim().toLowerCase()) {
            case "start":
                runServer();
                return true;
            case "stop":
                if (serverRunning) {
                    stopServer();
                    return false;
                } else {
                    System.out.println("Server not started. Enter 'start' to start server.");
                    return true;
                }
            default:
                System.out.println("Command not recognized. 'start' or 'stop' only");
                return true;
        }
    }

    // init server instance
    private void runServer() {
        this.serverFuture = threadPool.submit(serverInstance);
        System.out.println("Server started. Enter 'stop' to stop server.");
    }

    // stops the server instance immediately
    private void stopServer() {
        this.serverFuture.cancel(true);
        System.out.println("Server stopped");
    }
}