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
    private FileHandling fileHandler;

    public HttpClientConnection(Socket socket, FileHandling fileHandler) {
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

    // receive request from browser
    private String[] receiveFromBrowser() throws IOException {
        String request = reader.readLine();
        String[] requestArgs = request.split(" ");
        return requestArgs;
    }

    // handle request and sends response to browser
    private void parseRequest(String[] requestArgs) {
        // check if method is GET
        String method = requestArgs[0];
        String url = requestArgs[1];

        if (!method.equals("GET")) {
            this.write405(method);
            return;
        }

        // check if File exists
        Optional<String> fileOpt = fileHandler.fileExists(url);
        if (!fileOpt.isPresent()) {
            this.write404(url);
            return;
        } else {
            String fileDir = fileOpt.get();
            if (fileHandler.isPNG(url)) {
                this.writePNG(fileDir);
                return;
            }
            this.writePage(fileDir);
            return;
        }
    }

    private void write405(String method) {
        // sends 405 Method Not Allowed
        String header = "HTTP/1.1 405 Method Not Allowed";
        String resText = method + " not supported";
        try {
            writeToClient(header, resText);
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

    private void write404(String url) {
        // sends 404 Not Found
        String header = "HTTP/1.1 404 Not Found";
        String resText = url + " not found";
        try {
            writeToClient(header, resText);
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

    private void writePage(String fileDir) {
        // sends 200 OK and the file requested
        String header = "HTTP/1.1 200 OK";
        try {
            byte[] htmlBytes = fileHandler.getFile(fileDir);
            writeToClient(header, htmlBytes, null);
        } catch (IOException e) {
            System.err.println("Error reading file");
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

    private void writePNG(String fileDir) {
        // sends 200 OK and PNG image
        String header = "HTTP/1.1 200 OK";
        String contentType = "Content-Type: image/png";
        try {
            byte[] imageBytes = fileHandler.getFile(fileDir);
            writeToClient(header, imageBytes, contentType);
        } catch (IOException e) {
            System.err.println("Error reading file");
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
        return;
    }

    private void writeToClient(String header, String content) throws Exception {
        // writes response to browser using writeString
        writer.writeString(header);
        writer.writeString();
        writer.writeString(content);
        writer.close();
    }

    private void writeToClient(String header, byte[] byteArray, String contentType) throws Exception {
        // writes response to browser using writeBytes
        writer.writeString(header);
        if (contentType != null) {
            writer.writeString(contentType);
        }
        writer.writeString();
        writer.writeBytes(byteArray);
        writer.close();
    }

}