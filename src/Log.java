import java.util.Scanner;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Log {

    private static final HashMap<String, String> map = new HashMap<>();
    public static final ScheduledExecutorService schedueler = Executors.newScheduledThreadPool(1);
    public static final Scanner input = new Scanner(System.in);

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
                    twoFactor();
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
        //input.close();
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
        while(tries < 3 && !verified) {
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
            input.nextLine();

        }
    }
    public static void twoFactor()
    {
        /*
        Problemet er her, når der gået 10 sekunder og den refresher og laver en ny kode, så får jeg den her error
        Exception in thread "Thread-1" java.lang.IndexOutOfBoundsException: end. Har prøvet at lav en
        try and catch men synes ikke jeg kunne få det til at virke
         */


        Runnable refreshTask = new Runnable()
        {
            @Override
            public void run()
            {
                int rand = codeGen(9000);
                System.out.println(rand);
                System.out.println("Skriv 2fa");

               new Thread(() -> {

                   int twoFactor = input.nextInt();
                    if (twoFactor == rand)
                    {
                        System.out.println("Rigtigt");
                    }
                    else
                    {
                        System.out.println("Forkert 2fa");
                    }


               }).start();
            }
        };
        schedueler.scheduleAtFixedRate(refreshTask, 0,10, TimeUnit.SECONDS);
    }
    public static int codeGen(int numberscale)
    {
        int random = (int) (Math.random() * numberscale);
        return 1000 + random;
    }




}