public class StreamCipher {
    // Encrypt or decrypt using stream cipher logic
    public static char[] process(char[] input, char[] key) {
        char[] output = new char[input.length];

        for (int i = 0; i < input.length; i++) {
            // XOR each byte with key, looping key if shorter than input
            output[i] = (char) (input[i] ^ key[i % key.length]);
        }

        return output;
    }
}
