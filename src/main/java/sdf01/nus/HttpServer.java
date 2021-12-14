package sdf01.nus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private FileHandling fileHandler;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    // constructor
    public HttpServer(int port, String[] docRoot) {
        this.port = port;
        this.fileHandler = new FileHandling(docRoot);
    }

    public void start() {
        this.initServer();
        System.out.println("server started.");
        try {
            this.startServer();
        } catch (IOException e) {
            System.err.println("Socket error.");
            System.exit(1);
        }
        return;
    }

    private void initServer() {
        // try to start serverSocket on the port. exits if failed
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Failed to start serverSocket on port " + port);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("port " + port + " invalid.");
        }

        // checks validity of docRoot
        if (!this.fileHandler.checkDocRoot()) {
            System.err.println("Invalid directory found in docRoot.");
            System.exit(1);
        }

        // starts thread pool
        this.threadPool = Executors.newFixedThreadPool(3);
    }

    private void startServer() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            threadPool.submit(new HttpClientConnection(socket, fileHandler));
        }
    }
}
