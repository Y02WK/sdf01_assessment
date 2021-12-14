package sdf01.nus;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public static void main(String[] args) {
    }

    // constructor
    public HttpServer(int port, String[] docRoot) {
        // init serverSocket

        // start serverSocket

        // init threadPool
    }

    private void initServer() {
        this.serverSocket = new ServerSocket(port);
        this.threadPool = Executors.newFixedThreadPool(3);

        // check if connected
        // check docRoot 2nd method
    }

    private void startServer() {
        // accept incoming connections on the socket

        // new thread from HttpClientConnection

        // submit thread to the threadpool
    }

}
