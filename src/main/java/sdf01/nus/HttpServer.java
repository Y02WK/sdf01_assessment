package sdf01.nus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer implements Runnable {
    private int port;
    private FileHandling fileHandler;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public HttpServer(int port, String[] docRoot) {
        this.port = port;
        this.fileHandler = new FileHandling(docRoot);
    }

    public void run() {
        this.initServer();
        try {
            this.acceptConnections();
        } catch (IOException e) {
            System.err.println("Socket error.");
            System.exit(1);
        } catch (InterruptedException e) {
            shutdownServer();
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
            System.exit(1);
        }

        // checks validity of docRoot
        if (!this.fileHandler.checkDocRoot()) {
            System.err.println("Invalid directory found in docRoot.");
            System.exit(1);
        }

        // starts thread pool
        this.threadPool = Executors.newFixedThreadPool(3);
    }

    private void acceptConnections() throws IOException, InterruptedException {
        while (!Thread.interrupted()) {
            Socket socket = serverSocket.accept();
            threadPool.submit(new HttpClientConnection(socket, fileHandler));
        }
        throw new InterruptedException("Server interrupted");
    }

    private void shutdownServer() {
        threadPool.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
