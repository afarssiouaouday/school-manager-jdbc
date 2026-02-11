package com.app.ui;

import com.app.model.Student;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentTablePrinter {
    private static final String format = "| %-4s | %-20s | %-30s | %-3s | %-16s |%n";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void printStudentTable(List<Student> students){

        if(students == null || students.isEmpty()){
            System.out.println("Aucun étudiant");
            return;
        }

        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf(format, "ID", "Nom", "Email", "Âge", "Créé le");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (Student student : students)  printRow(student);

        System.out.println("-------------------------------------------------------------------------------------------");

    }

    public static void printRow(Student student){

        if(student == null) return;

        String createAt = student.getCreated_at() != null
                ? student.getCreated_at().format(formatter)
                : "N/A";

        System.out.printf(
                format,
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                createAt
        );
    }

}
