import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Enter INN: (must be 12 digits, ex. '009715225506')");
        String inn = in.nextLine();
        System.out.println("You entered inn: " + inn);

        System.out.println("");

        System.out.println("Enter FACTORY NUMBER: (must be 14 digits, ex. '00308300087104')");
        String factoryNumber = in.nextLine();
        System.out.println("You entered factory number: " + factoryNumber);

        String resultRNM = CRC.calculate(inn, factoryNumber);
        System.out.println("");
        System.out.println("----------------------------------------");
        System.out.println("Generated RNM: " + resultRNM);

        in.close();

        //System.out.println(CRC.calculate("009715225506", "00308300087104"));
    }
}
