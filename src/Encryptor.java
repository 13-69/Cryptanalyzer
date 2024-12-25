import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Encryptor {
    private static Encryptor encryptor;

    private Encryptor() {
    }

    public static Encryptor getEncryptor() {
        if (encryptor == null) {
            encryptor = new Encryptor();
        }

        return encryptor;
    }

    public void encrypt(int key, String alphabet, Path input, Path output) {
        createFile(output);

        key = key % alphabet.length();

        try (BufferedReader reader = new BufferedReader(new FileReader(input.toString()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(output.toString()))) {
            while (reader.ready()) {
                String character = String.valueOf((char) reader.read());
                int indexInAlphabet = alphabet.indexOf(character);

                if (indexInAlphabet != -1) {
                    int newIndexInAlphabet = (indexInAlphabet + key) % alphabet.length();
                    writer.write(alphabet.charAt(newIndexInAlphabet));
                } else {
                    writer.write(character);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void decrypt(int key, String alphabet, Path input, Path output) {
        key = key % alphabet.length();

        key = alphabet.length() - key;

        encrypt(key, alphabet, input, output);
    }

    public int bruteForce(String alphabet, List<String> theMostPopularWords, Path input, Path output) {
        HashMap<Integer, Integer> keysScore = new HashMap<>();

        for (int key = 0; key < alphabet.length(); key++) {
            decrypt(key, alphabet, input, output);

            try (BufferedReader reader = new BufferedReader(new FileReader(output.toString()))) {
                while (reader.ready()) {
                    String line = reader.readLine();
                    scoring(key, line, keysScore, theMostPopularWords);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        int mostLikelyKey = selectKey(keysScore);

        decrypt(mostLikelyKey, alphabet, input, output);

        return mostLikelyKey;
    }

    private void scoring(int key, String line, HashMap<Integer, Integer> keysScore, List<String> theMostPopularWords) {
        for (String theMostPopularWord : theMostPopularWords) {
            if (line.contains(theMostPopularWord)) {
                if (keysScore.containsKey(key)) {
                    int keyScore = keysScore.get(key);
                    keysScore.put(key, keyScore + 1);
                } else {
                    keysScore.put(key, 1);
                }
            }
        }
    }

    private int selectKey(HashMap<Integer, Integer> keysScore) {
        int maxValue = 0;
        int mostLikelyKey = 0;

        for (Map.Entry<Integer, Integer> entry : keysScore.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            if (value > maxValue) {
                maxValue = value;
                mostLikelyKey = key;
            }
        }

        return mostLikelyKey;
    }

    private void createFile(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
