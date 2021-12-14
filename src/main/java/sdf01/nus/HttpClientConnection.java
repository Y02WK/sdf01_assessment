package sdf01.nus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
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
        String header = "HTTP/1.1 405 Method Not Allowed";
        String resText = method + " not supported";
        try {
            writer.writeString(header);
            writer.writeString();
            writer.writeString(resText);
            writer.close();
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

    private void write404(String url) {
        String header = "HTTP/1.1 404 Not Found";
        String resText = url + " not found";
        try {
            writer.writeString(header);
            writer.writeString();
            writer.writeString(resText);
            writer.close();
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

    private void writePage(String fileDir) {
        String header = "HTTP/1.1 200 OK";
        List<String> contents = fileHandler.getContents(fileDir);
        try {
            // write header
            writer.writeString(header);
            writer.writeString();
            for (String html : contents) {
                writer.writeString(html);
            }
            writer.close();
            return;
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
    }

    private void writePNG(String fileDir) {
        // TODO
        String header = "HTTP/1.1 200 OK";
        String contentType = "Content-Type: image/png";
        try {
            byte[] imageBytes = fileHandler.getPNG(fileDir);
            writer.writeString(header);
            writer.writeString(contentType);
            writer.writeString();
            writer.writeBytes(imageBytes);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error reading PNG file");
        } catch (Exception e) {
            System.err.println("Error in sending response to browser");
        }
        return;
    }

}