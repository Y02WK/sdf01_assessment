package sdf01.nus;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private String[] docRoot;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public static void main(String[] args) {
    }

    // constructor
    public HttpServer(int port, String[] docRoot) {
        this.port = port;
        this.docRoot = docRoot;
        // init serverSocket

        // start serverSocket

        // init threadPool
    }

    public void start() {
        this.initServer();
        this.startServer();
        return;
    }

    private void initServer() {
        // try to start serverSocket on the port. exits if failed
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Failed to start serverSocket on port " + port);
            System.exit(1);
        }
        this.threadPool = Executors.newFixedThreadPool(3);

        // check if connected
        // check docRoot 2nd method
    }

    private void startServer() {
        // accept incoming connections on the socket

        // new thread from HttpClientConnection

        // submit thread to the threadpool
    }

    private boolean checkDocRoot() {
        // if checks pass return true
        // else return false

        // check if exists + check if directory (Files.isDirectory())
        // check if readable (Files.isReadable())
        for (String dir : docRoot) {
            Path dirPath = Path.of(dir);
            if (!Files.isReadable(dirPath) || !Files.isDirectory(dirPath)) {
                return false;
            }
        }
        return true;
    }
}
