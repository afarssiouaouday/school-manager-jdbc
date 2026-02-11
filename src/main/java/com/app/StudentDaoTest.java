package com.app;

import com.app.dao.StudentDAOImpl;
import com.app.model.Student;

public class StudentDaoTest {

    public static void main(String[] args) {

        StudentDAOImpl dao = new StudentDAOImpl();

//        long id = dao.addStudent(new Student("Aouday","aouday@gmail.com",22));
//        System.out.println("Insert OK, id = "+id);

        System.out.println("all students");
        dao.getAllStudents().forEach(System.out::println);

        System.out.println("student by id");
        Student student = dao.getStudentById(1L);
        System.out.println(student);
    }
}
