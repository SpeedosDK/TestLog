import java.util.Scanner;
import java.util.HashMap;
public class Log {
    public static final Scanner input = new Scanner(System.in);
    private static final HashMap<String, String> map = new HashMap<>();

    public static void main(String[] args)
    {
        System.out.println("Velkommen");
        System.out.println("1. Vil du lave en bruger?");
        System.out.println("2. Vil du log ind?");
        System.out.println("3. Admin");
        System.out.println("4. Exit");

        boolean repeat;
        do {
            repeat = false;
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    accountCreate();
                    accountLogin();
                    verification();
                    break;
                case 2:
                    accountLogin();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    System.out.println("Programmet lukker ned");
                    input.close();
                    break;

                default:
                    System.out.println("Indtastet forkert input. Prøv igen");
                    repeat = true;
            }
        } while (repeat);
        input.close();
    }

    public static void accountCreate()
    {
        System.out.println("Skriv brugernavn");
        String username = input.nextLine();
        System.out.println("Skriv password");
        String password = input.nextLine();

        if (map.get(username) != null)
        {
            System.out.println("Brugernavn findes allerede ");
        }
        map.put(username, password);
        System.out.println("Bruger er lavet");
    }

    public static void accountLogin()
    {
        System.out.println("Skriv dit brugernavn");
        String username = input.nextLine();
        System.out.println("Skriv password");
        String password = input.nextLine();

        if (map.containsKey(username) && password.equals(map.get(username)))
        {
            System.out.println("Du er logget ind");
        }
        else
        {
            System.out.println("Forkert brugernavn eller password");
        }
    }
    public static void adminLogin()
    {
        System.out.println("Skriv admin loginddet");
        String username = input.nextLine();
        System.out.println("Skriv password");
        String password = input.nextLine();
        if (username.equals("admin") && password.equals("admin"))
        {

            System.out.println("Du er kommet ind i admin panelet");
            System.out.println("Vil du se accounts");
            String valg = input.nextLine();

            if (valg.equals("ja"))
            {
                System.out.println(map.get(username));
            }
        }
    }
    public static void verification()
    {
        System.out.println("Account skal blive verificeret, skriv koden");
        int rand = codeGen(9999);
        System.out.println(rand);
        int tries = 0;
        boolean verified = false;
        while(tries < 3) {
            int code = input.nextInt();
            if (rand == code) {
                System.out.println("Du er blevet verificeret");
                verified = true;
            } else
            {
                tries++;
                System.out.println("Prøv igen. Du har brugt " + tries + " forsøg");
            }
            if (!verified)
            {
                System.out.println("Verifikation mislykkede");
            }
        }
    }

    public static int codeGen(int numberscale)
    {
        int random = (int) (Math.random() * numberscale);
        return 1000 + random;
    }

}