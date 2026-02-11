package com.app.ui;

import java.util.Scanner;

public class InputReader {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final Scanner scanner;


    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            try{
                return Integer.parseInt(line);
            }catch (NumberFormatException e){
                System.out.println(ConsoleColors.YELLOW + "Entre un nombre entier." + ConsoleColors.RESET);
            }
        }
    }


    public String readString(String prompt){
        while(true){
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            if(!line.isBlank()) return line;

            System.out.println(ConsoleColors.YELLOW + "Champ obligatoire." + ConsoleColors.RESET);
        }
    }

    public Long readLong(String prompt){
        while(true){
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try{
                return Long.parseLong(line);
            }catch (NumberFormatException e){
                System.out.println(ConsoleColors.YELLOW + " Entre un nombre ." + ConsoleColors.RESET);
            }
        }
    }

    public String readEmail(){
        while(true){
            System.out.print("Email: ");

            String line = scanner.nextLine().trim();

            if(line.isBlank()){
                System.out.println(ConsoleColors.YELLOW + "Champ obligatoire." + ConsoleColors.RESET);
                continue;
            }
            if(!line.matches(EMAIL_PATTERN)){
                System.out.println(ConsoleColors.YELLOW + "L'email doit être au format : exemple@domaine.com" + ConsoleColors.RESET);
                continue;
            }
            return line;
        }
    }
}
