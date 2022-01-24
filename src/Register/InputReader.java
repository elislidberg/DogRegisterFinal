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

    // förtydliga namn

    public int inputIntNr(String text){
        System.out.print(text + "?> ");
        int numberInput = input.nextInt();
        input.nextLine();
        return numberInput;
    }

    public double inputDoubleNr(String text){
        System.out.print(text + "?> ");
        double doubleInput = input.nextDouble();
        input.nextLine();
        return doubleInput;
    }

    public String inputString(String text) {
        String inputText = "";
        while (inputText.trim().length() == 0){
            System.out.print(text + "?> ");
            inputText = input.nextLine();
            if (inputText.trim().length() == 0){
                System.out.println("Error: the name can´t be empty");
            }
        }
        return inputText.trim();
    }
}

