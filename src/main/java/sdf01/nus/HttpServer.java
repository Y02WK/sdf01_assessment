package sdf01.nus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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

        // checks validity of docRoot
        if (!this.checkDocRoot()) {
            System.err.println("Invalid directory found in docRoot.");
            System.exit(1);
        }

        // starts thread pool
        this.threadPool = Executors.newFixedThreadPool(3);
    }

    private void startServer() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept(); // accept incoming connections on the socket
            threadPool.submit(new HttpClientConnection(socket)); // submit thread to the threadpool
        }

        // new thread from HttpClientConnection

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
