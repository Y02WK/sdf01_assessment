package sdf01.nus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpClientConnection implements Runnable {
    private Socket socket;
    private HttpWriter writer;
    private BufferedReader reader;
    // new requestparser
    // FileHandling

    public HttpClientConnection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        initStreams();
        try {
            String request = receiveFromBrowser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initStreams() {
        // init input and output streams
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new HttpWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error opening i/o streams.");
        }
        return;
    }

    // use HttpWriter

    // receive request from browser
    private String[] receiveFromBrowser() throws IOException {
        String request = reader.readLine();
        String[] requestArgs = request.split(" ");
        return requestArgs;
    }

    // ParseRequest
    private void parseRequest(String[] requestArgs) {
        // check if method is GET
        String method = requestArgs[0];
        String url = requestArgs[1];

        if (!method.equals("GET")) {
            return;
        }

        // check if File exists
        // goes through each dir and checks if the file exists

        // check PNG
    }

    // Send to browser using HttpWriter

}