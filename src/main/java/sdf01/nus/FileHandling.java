package sdf01.nus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileHandling {
    private String[] docRoot;

    public FileHandling(String[] docRoot) {
        this.docRoot = docRoot;
    }

    protected boolean checkDocRoot() {
        // if checks pass return true, else return false

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

    private String handleIndex(String filename) {
        // Checks if the page request is "/" and changes it to index
        if (filename.equals("/")) {
            return "/index.html";
        } else {
            return filename;
        }
    }

    protected Optional<String> fileExists(String filename) {
        String parsedFilename = handleIndex(filename);
        String dirFileString;
        // search all the directories for the file
        for (String dir : docRoot) {
            dirFileString = dir + parsedFilename;
            Path dirFilePath = Path.of(dirFileString);
            if (dirFilePath.toFile().exists()) {
                // returns an optional containing the Path as a String
                return Optional.ofNullable(dirFileString);
            }
        }
        // return empty Optional if file not found
        return Optional.empty();
    }

    protected boolean isPNG(String filename) {
        // returns true if the requested file is a .png
        if (filename.endsWith(".png")) {
            return true;
        }
        return false;
    }

    protected byte[] getFile(String filename) throws IOException {
        // return all bytes in the file as array
        Path filePath = Path.of(filename);
        InputStream fileStream = Files.newInputStream(filePath);
        return fileStream.readAllBytes();
    }
}
