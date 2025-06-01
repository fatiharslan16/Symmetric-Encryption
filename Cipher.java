public class Cipher {
    public static void main(String[] args) {
        if (args.length != 5) System.exit(1);

        String type = args[0];
        String inputPath = args[1];
        String outputPath = args[2];
        String keyPath = args[3];
        String mode = args[4];

        if (!type.equals("B") && !type.equals("S")) {
            System.out.println("Invalid Function Type");
            System.exit(1);
        }

        if (!mode.equals("E") && !mode.equals("D")) {
            System.out.println("Invalid Mode Type");
            System.exit(1);
        }

        char[] input = FileHandling.readFile(inputPath);
        char[] key = FileHandling.readKeyFile(keyPath);
        char[] result;

        if (type.equals("B")) {
            if (key.length != 16) System.exit(1); // Invalid block cipher key size
            if (mode.equals("E")) result = BlockCipher.encrypt(input, key);
            else result = BlockCipher.decrypt(input, key);
        } else {
            result = StreamCipher.process(input, key); // same method for both modes
        }

        FileHandling.writeFile(outputPath, result);
    }
}
