package com.app.ui;

import com.app.dao.StudentDAO;
import com.app.model.Student;
import com.app.util.CsvExporter;

import java.nio.file.Path;
import java.util.List;

public class StudentConsoleUI {

    private final StudentDAO studentDAO;
    private final InputReader input;

    public StudentConsoleUI(StudentDAO studentDAO, InputReader input) {
        this.studentDAO = studentDAO;
        this.input = input;
    }


    public void addStudent(){
        String name = input.readString("nom: ");
        String email = input.readEmail();
        int age = input.readInt("age: ");


        try{
            long id = studentDAO.addStudent(new Student(name, email, age));
            System.out.println(ConsoleColors.GREEN + "étudiant ajouté avec ID = " + id + ConsoleColors.RESET);
        }catch (Exception e){
            System.out.println(ConsoleColors.RED +"Error: "+ e.getMessage() + ConsoleColors.RESET);
        }
    }

    public void listStudents(){

        try{
            List<Student> students = studentDAO.getAllStudents();
            StudentTablePrinter.printStudentTable(students);
        }catch (Exception e){
            System.out.println(ConsoleColors.RED + "Error: "+ e.getMessage() + ConsoleColors.RESET);
        }
    }

    public void search(){
        while(true){
            int choice = input.readInt("taper 1 pour chercher par ID , 2 pour charcher par Email, 3 par nom (0 pour quitter):");

            if(choice == 0) break;

            else if(choice == 1 ) searchStudentByID();

            else if(choice == 2 ) searchStudentByEmail();

            else if(choice == 3 ) searchStudentByName();
        }
    }

    public void searchStudentByID(){

        while (true){
            Long id = input.readLong("ID(0 pour quitter):");

            if (id == 0) return;

            Student student = studentDAO.getStudentById(id);

            if(student == null){
                System.out.println(ConsoleColors.YELLOW + "Student n'existe pas !" + ConsoleColors.RESET);
                continue;
            }
            StudentTablePrinter.printRow(student);
        }

    }

    public void searchStudentByEmail(){
            String email = input.readEmail();

            System.out.println("(0 pour quitter):");

            if ("0".equals(email)) return;

            Student student = studentDAO.getStudentByEmail(email);

            if(student == null){
                System.out.println(ConsoleColors.YELLOW + "Student n'existe pas !" + ConsoleColors.RESET);

        }
        StudentTablePrinter.printRow(student);

    }

    public void searchStudentByName(){

        while (true){
            String name = input.readString("name(0 pour quitter):");

            if ("0".equals(name)) return;

            List<Student> students = studentDAO.getStudentsByName(name);

            StudentTablePrinter.printStudentTable(students);
        }

    }

    public void updateStudent(){
        long id;
        while (true){
            id = input.readLong("ID(0 pour quitter):");

            if(id == 0) return;

            Student student = studentDAO.getStudentById(id);
            if(student == null){
                System.out.println(ConsoleColors.YELLOW + "Student n'existe pas !" + ConsoleColors.RESET);
                continue;
            }
            else{
                System.out.println("tu vas modifier cet étudiant:");
                System.out.println(student);
                String confirm = input.readString("tape 'ok' pour continuer:").trim();
                if(!"ok".equals(confirm)){
                    System.out.println("modification annulée.");
                    continue;
                }
                String name = input.readString("Nouveau nom: ");
                int age = input.readInt("Nouvel age: ");

                boolean res = studentDAO.updateStudent(name, age, id);
                if (res)
                    System.out.println(ConsoleColors.GREEN + "L'étudiant avec l'ID = " + id + " est modifié avec succès." + ConsoleColors.RESET);
                else
                    System.out.println(ConsoleColors.RED + "échec de la modification pour ID: " + id + ConsoleColors.RESET);
                return;
            }
        }

    }

    public void deleteStudent(){

        while (true){
            long id = input.readLong("ID(0 pour quitter):");
            if(id == 0) return;

            Student student = studentDAO.getStudentById(id);
            if(student == null){
                System.out.println(ConsoleColors.YELLOW + "Student n'existe pas !" + ConsoleColors.RESET);
                continue;
            }
            System.out.println("tu veux supprimer cet étudiant:");
            System.out.println(student);
            String confirm = input.readString("Tape 'ok' pour pour supprimer: ").trim();

            if(!"ok".equals(confirm)){
                System.out.println("suppression  annulée.");
                continue;
            }

            boolean res = studentDAO.deleteStudent(id);

            if (res)
                System.out.println(ConsoleColors.GREEN + "L'étudiant avec l'ID = " + id + " est supprimée avec succès." + ConsoleColors.RESET);
            else
                System.out.println(ConsoleColors.RED + "échec de la modification pour ID: " + id + ConsoleColors.RESET);
            return;
        }
    }


    public void exportStudentsToCsv() {

        try{
            List<Student> students = studentDAO.getAllStudents();

            String filePath = input.readString("Chemin du fichier CSV (ex: students.csv): ");
            CsvExporter.exportStudents(Path.of(filePath), students);

            System.out.println(ConsoleColors.GREEN +
                    "Export terminé: " + filePath + " (" + students.size() + " étudiants)" +
                    ConsoleColors.RESET);

        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Export échoué: " + e.getMessage() + ConsoleColors.RESET);
        }

        }

 }
