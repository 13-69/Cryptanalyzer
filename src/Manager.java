import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Manager {

    private static Manager manager;
    private final Path pathOfAlphabet = Path.of("Resources/Alphabet.txt");
    private final Path pathOfData = Path.of("Resources/Data.txt");
    private final Path pathOfEncryptedFile = Path.of("Resources/Encrypted.txt");
    private final Path pathOfDecryptedFile = Path.of("Resources/Decrypted.txt");
    private final Path pathOfBruteForceResult = Path.of("Resources/BruteForce.txt");
    private final Path pathOfTheMostPopularWords = Path.of("Resources/TheMostPopularWords");
    private final String alphabet = Files.readAllLines(pathOfAlphabet).toString().replaceAll("[\\[\\]]", "");
    private final List<String> theMostPopularWords = Files.readAllLines(pathOfTheMostPopularWords);

    private Manager() throws IOException {
    }

    public static Manager getManager() {
        if (manager == null) {
            try {
                manager = new Manager();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return manager;
    }

    public void encrypt(int key) {
        Encryptor encryptor = Encryptor.getEncryptor();

        encryptor.encrypt(key, alphabet, pathOfData, pathOfEncryptedFile);
    }

    public void decrypt(int key) {
        Encryptor encryptor = Encryptor.getEncryptor();

        encryptor.decrypt(key, alphabet, pathOfEncryptedFile, pathOfDecryptedFile);
    }

    public void bruteForce() {
        Encryptor encryptor = Encryptor.getEncryptor();

        int key = encryptor.bruteForce(alphabet, theMostPopularWords, pathOfEncryptedFile, pathOfBruteForceResult);

        System.out.println("Most likely key: " + key);
    }
}
