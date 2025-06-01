import java.util.Arrays;

public class BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final char PAD_BYTE = (char) 0x81;

    public static char[] encrypt(char[] input, char[] key) {
        if (key.length != BLOCK_SIZE) System.exit(1);

        int totalBlocks = (int) Math.ceil(input.length / (double) BLOCK_SIZE);
        char[] paddedInput = padInput(input, totalBlocks * BLOCK_SIZE);
        char[] result = new char[paddedInput.length];

        for (int block = 0; block < totalBlocks; block++) {
            int start = block * BLOCK_SIZE;
            char[] blockData = Arrays.copyOfRange(paddedInput, start, start + BLOCK_SIZE);

            char[] xored = xorWithKey(blockData, key);
            char[] swapped = applySwap(xored, key);

            System.arraycopy(swapped, 0, result, start, BLOCK_SIZE);
        }

        return result;
    }

    public static char[] decrypt(char[] input, char[] key) {
        if (key.length != BLOCK_SIZE) System.exit(1);

        int totalBlocks = input.length / BLOCK_SIZE;
        char[] result = new char[input.length];

        for (int block = 0; block < totalBlocks; block++) {
            int start = block * BLOCK_SIZE;
            char[] blockData = Arrays.copyOfRange(input, start, start + BLOCK_SIZE);

            char[] unSwapped = reverseSwap(blockData, key);
            char[] decrypted = xorWithKey(unSwapped, key);

            System.arraycopy(decrypted, 0, result, start, BLOCK_SIZE);
        }

        return removePadding(result);
    }

    private static char[] xorWithKey(char[] block, char[] key) {
        char[] result = new char[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            result[i] = (char) (block[i] ^ key[i]);
        }
        return result;
    }

    private static char[] applySwap(char[] block, char[] key) {
        char[] result = block.clone();
        int start = 0;
        int end = BLOCK_SIZE - 1;
        int k = 0;

        while (start < end) {
            int mod = key[k % key.length] % 2;
            if (mod == 1) {
                char temp = result[start];
                result[start] = result[end];
                result[end] = temp;
                end--;
            }
            start++;
            k++;
        }

        return result;
    }

    private static char[] reverseSwap(char[] block, char[] key) {
        // To reverse the swap, simulate swap decisions forward, then apply them in reverse
        int start = 0;
        int end = BLOCK_SIZE - 1;
        int k = 0;

        // Record swap operations
        int[][] swaps = new int[BLOCK_SIZE][2];
        int swapCount = 0;

        while (start < end) {
            int mod = key[k % key.length] % 2;
            if (mod == 1) {
                swaps[swapCount][0] = start;
                swaps[swapCount][1] = end;
                end--;
                swapCount++;
            }
            start++;
            k++;
        }

        // Apply swaps in reverse order
        char[] result = block.clone();
        for (int i = swapCount - 1; i >= 0; i--) {
            int i1 = swaps[i][0];
            int i2 = swaps[i][1];
            char temp = result[i1];
            result[i1] = result[i2];
            result[i2] = temp;
        }

        return result;
    }

    private static char[] padInput(char[] input, int paddedLength) {
        char[] padded = Arrays.copyOf(input, paddedLength);
        Arrays.fill(padded, input.length, paddedLength, PAD_BYTE);
        return padded;
    }

    private static char[] removePadding(char[] input) {
        int actualLength = input.length;
        while (actualLength > 0 && input[actualLength - 1] == PAD_BYTE) {
            actualLength--;
        }
        return Arrays.copyOf(input, actualLength);
    }
}
