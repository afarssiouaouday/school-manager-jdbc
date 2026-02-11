package com.app.util;

import com.app.model.Student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvExporter {

    private CsvExporter() {}

    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void exportStudents(Path file, List<Student> students) throws IOException {

        if (file == null) throw new IllegalArgumentException("file is null");

        if(students == null) students = List.of();

        Path parent = file.getParent();

        if(parent != null) Files.createDirectories(parent);

        try(BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {

            w.write("id,name,email,age,created_at");
            w.newLine();

            for(Student student : students) {

                String createdAt = (student.getCreated_at() != null) ? dateFormat.format(student.getCreated_at()) : "";

                String line = String.join(",",
                        csv(String.valueOf(student.getId())),
                        csv(student.getName()),
                        csv(student.getEmail()),
                        csv(String.valueOf(student.getAge())),
                        csv(createdAt)
                        );

                w.write(line);
                w.newLine();
            }
        }
    }


    private static String csv(String value) {

        if(value == null) return "";

        boolean mustQuote = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r");
        String v = value.replace("\"", "\"\"");

        return mustQuote ? "\"" + v + "\"" : v;
    }
}
