import java.util.Scanner;

public class Starter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = Manager.getManager();
        boolean isWork = true;
        String message =
                """
                        Enter the action number:
                        1. Encrypt
                        2. Decrypt
                        3. Brute Force
                        4. Exit
                        """;

        while (isWork) {
            System.out.println(message);

            String inputLine = scanner.nextLine();
            int number;

            try {
                number = Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                System.out.println("I said the number!\n");
                continue;
            }


            if (number == 1) {
                System.out.println("Enter the key:");

                String line = scanner.nextLine();
                int key = Math.abs(Integer.parseInt(line));

                manager.encrypt(key);
            } else if (number == 2) {
                System.out.println("Enter the key:");

                String line = scanner.nextLine();
                int key = Math.abs(Integer.parseInt(line));

                manager.decrypt(key);
            } else if (number == 3) {
                manager.bruteForce();
            } else {
                isWork = false;
            }
        }
    }
}
