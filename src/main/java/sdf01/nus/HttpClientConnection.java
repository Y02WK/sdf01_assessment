package sdf01.nus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;

public class HttpClientConnection implements Runnable {
    private Socket socket;
    private HttpWriter writer;
    private BufferedReader reader;
    private FilesHandler fileHandler;

    public HttpClientConnection(Socket socket, FilesHandler fileHandler) {
        this.socket = socket;
        this.fileHandler = fileHandler;
    }

    public void run() {
        initStreams();
        try {
            parseRequest(receiveFromBrowser());
            socket.close();
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
            this.write405(method);
        }

        // check if File exists
        Optional<String> fileDir = fileHandler.fileExists(url);
        if (!fileDir.isPresent()) {
            this.write404(url);
        } else {
            // 200 OK
        }

        // goes through each dir and checks if the file exists

        // check PNG
    }

    private void write405(String method) {
        String response = "HTTP/1.1 405 Method Not Allowed";
        String resText = method + " not supported";
        try {
            writer.writeString(response);
            writer.writeString();
            writer.writeString(resText);
            writer.close();
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

    private void write404(String url) {
        String response = "HTTP/1.1 404 Not Found";
        String resText = url + " not found";
        try {
            writer.writeString(response);
            writer.writeString();
            writer.writeString(resText);
            writer.close();
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

}