package Register;
// Elis Lidberg elli6378

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
    private static List<InputStream> listOfStreams = new ArrayList<>();
    private Scanner input;

    public InputReader(InputStream inputStream){
        if(listOfStreams.contains(inputStream)){
            throw new IllegalStateException("Input stream existerar redan!");
        }
        listOfStreams.add(inputStream);
        Scanner scanner = new Scanner(inputStream);
        this.input = scanner;
    }

    public InputReader(){
        this(System.in);
    }

    public int intNr(String text){
        System.out.print(text + "?> ");
        int i = input.nextInt();
        input.nextLine();
        return i;
    }

    public double doubleNr(String text){
        System.out.print(text + "?> ");
        double i = input.nextDouble();
        input.nextLine();
        return i;
    }

    public String string(String text) {
        String i = "";
        while (i.trim().length() == 0){
            System.out.print(text + "?> ");
            i = input.nextLine();
            if (i.trim().length() == 0){
                System.out.println("Error: the name can´t be empty");
            }
        }
        return i.trim();
    }
}

