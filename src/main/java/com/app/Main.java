package com.app;

import com.app.dao.StudentDAO;
import com.app.dao.StudentDAOImpl;
import com.app.ui.InputReader;
import com.app.ui.StudentConsoleUI;
import com.app.ui.StudentTablePrinter;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("==== Scholl Manager ====");

        try(Scanner scanner = new Scanner(System.in)) {
            StudentDAO dao = new StudentDAOImpl();
            InputReader input =  new InputReader(scanner);
            StudentTablePrinter printer = new StudentTablePrinter();
            StudentConsoleUI ui = new StudentConsoleUI(dao, input);

            while (true) {
                printMenu();
                int choice = input.readInt("Choix: ");

                switch (choice){
                    case 1 -> ui.addStudent();
                    case 2 -> ui.listStudents();
                    case 3 -> ui.search();
                    case 4 -> ui.updateStudent();
                    case 5 -> ui.deleteStudent();
                    case 6 -> ui.exportStudentsToCsv();
                    case 0 -> {
                        System.out.println("Bye");
                        return;
                    }
                    default -> {
                        System.out.println("choix invalide. Réessaie.");
                    }
                }
            }
        }

    }

    private void printMenu(){
        System.out.print("""
                ----MENU----
                1- Ajouter un étudiant
                2- afficher tous les étudiants
                3- Chercher un étudiant par ID
                4- Modifier un étudiant
                5- Supprimer un étudiant
                6- exporter en CSV
                0- Quitter
                ----------- 
                """);
    }

}