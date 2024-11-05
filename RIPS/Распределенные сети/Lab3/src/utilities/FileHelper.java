package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHelper {

    private final String fileName;
    private static Scanner scanner;

    public FileHelper(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        scanner = new Scanner(new File(this.fileName));
    }

    public String getFileName() {
        return fileName;
    }

    public String getTextFromFile(){
        var text = new StringBuilder();
        while(scanner.hasNext())
            text.append(scanner.nextLine()).append("\n");
        return text.toString();
    }
}
