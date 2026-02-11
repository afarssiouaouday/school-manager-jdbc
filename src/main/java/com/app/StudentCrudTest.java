package com.app;

import com.app.dao.StudentDAO;
import com.app.dao.StudentDAOImpl;
import com.app.model.Student;

public class StudentCrudTest {

    public static void main(String[] args) {
        StudentDAO dao = new StudentDAOImpl();

        long id = dao.addStudent(new Student("Test", "test@mail.com", 20));
        System.out.println("Insert OK, id=" + id);

        Student student = dao.getStudentById(id);
        System.out.println("findById =>  "+ student);

        student.setName("Test Update");
        boolean result = dao.updateStudent(student);
        System.out.println("Update OK, result=" + result);

        boolean deleted = dao.deleteStudent(id);
        System.out.println("Delete OK, deleted=" + deleted);
    }
}
