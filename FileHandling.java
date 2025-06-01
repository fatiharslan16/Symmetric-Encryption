import java.io.IOException;
import java.nio.file.*;

public class FileHandling {
    public static char[] readFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("Input File Does Not Exist");
            System.exit(1);
        }

        try {
            byte[] bytes = Files.readAllBytes(path);
            char[] chars = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                chars[i] = (char) (bytes[i] & 0xFF);
            }
            return chars;
        } catch (IOException e) {
            System.out.println("Input File Does Not Exist");
            System.exit(1);
        }

        return null;
    }

    public static char[] readKeyFile(String keyFilePath) {
        Path path = Paths.get(keyFilePath);
        if (!Files.exists(path)) {
            System.out.println("Key File Does Not Exist");
            System.exit(1);
        }

        try {
            byte[] bytes = Files.readAllBytes(path);
            char[] chars = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                chars[i] = (char) (bytes[i] & 0xFF);
            }
            return chars;
        } catch (IOException e) {
            System.out.println("Key File Does Not Exist");
            System.exit(1);
        }

        return null;
    }

    public static void writeFile(String outputPath, char[] content) {
        byte[] bytes = new byte[content.length];
        for (int i = 0; i < content.length; i++) {
            bytes[i] = (byte) content[i];
        }

        try {
            Files.write(Paths.get(outputPath), bytes);
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
