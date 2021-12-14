package sdf01.nus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FilesHandler {
    private String[] docRoot;

    public FilesHandler(String[] docRoot) {
        this.docRoot = docRoot;
    }

    public boolean checkDocRoot() {
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

    public Optional<String> fileExists(String filename) {
        String parsedFilename = handleIndex(filename);
        String dirFileString;
        Optional<String> result;
        // search all the directories for the file
        for (String dir : docRoot) {
            dirFileString = dir + parsedFilename;
            Path dirFilePath = Path.of(dirFileString);
            if (dirFilePath.toFile().exists()) {
                // returns an optional containing the Path as a String
                return result = Optional.ofNullable(dirFileString);
            }
        }
        // return empty Optional if file not found
        return result = Optional.empty();
    }
}
