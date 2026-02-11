package com.app.dao;

import com.app.model.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student getStudentByEmail(String email);
    List<Student> getStudentsByName(String name);
    Long addStudent(Student student);
    boolean updateStudent(Student student);
    boolean updateStudent(String name, int age, Long id);
    boolean deleteStudent(Long id);
}
