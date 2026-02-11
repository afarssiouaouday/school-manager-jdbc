package com.app.dao;

import com.app.model.Student;
import com.app.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private static final String INSERT_SQL = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";

    private static final String SELECT_SQL = "SELECT * FROM students";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM students WHERE id = ?";

    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM students WHERE email = ?";

    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM students WHERE name LIKE ? ORDER BY name ASC";

    private static final String UPDATE_SQL = "UPDATE students SET name = ?, age = ? WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM students WHERE id = ?";


    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_SQL);
            ResultSet rs = ps.executeQuery();
                ){
            while(rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");

                Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime created_at = ts == null ? null : ts.toLocalDateTime();

                Student student = new Student(id, name, email, age, created_at);
                students.add(student);
            }
        }catch (SQLException e){
            throw new RuntimeException("Error getting all students"+ e.getMessage());
        }
        return students;
    }

    @Override
    public Student getStudentById(Long id) {
        if(id <=0) throw new RuntimeException("Student id is less than 0");

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapRowToStudent(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting student by id"+ e.getMessage());
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_BY_EMAIL_SQL)
        ) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapRowToStudent(rs);
                }
                return null;
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error getting student by email"+ e.getMessage());
        }
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        if(name == null ||name.isBlank()) throw new RuntimeException("Student name is null or empty");

        List<Student> students = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME_SQL)
        ) {
            ps.setString(1,  name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Student student = mapRowToStudent(rs);
                    students.add(student);
                }
                return students;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting student by name"+ e.getMessage());
        }
    }

    @Override
    public Long addStudent(Student student) {

        if(student == null) throw new IllegalArgumentException("student is null");
        if(student.getName() == null) throw new IllegalArgumentException("student name is required");
        if(student.getEmail() == null) throw new IllegalArgumentException("student email is required");
        if(student.getAge() <= 0) throw new IllegalArgumentException("age must be > 0");

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());

            int rows = ps.executeUpdate();

            if(rows == 0) throw new SQLException("Insert failed");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()) {
                    Long generatedId = keys.getLong(1);
                    student.setId(generatedId);
                    return generatedId;
                }
                else {
                    throw new SQLException("Insert failed");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("Email already exists : " + student.getEmail());
        }
        catch (SQLException e) {
            throw new RuntimeException("Error SQL : " + e.getMessage());
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        if(student == null) throw new IllegalArgumentException("student is null");

        validateStudent(student, true);

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
        ){
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setLong(3, student.getId());

            int rows = ps.executeUpdate();

            return rows > 0;

        }catch (SQLException e){
            throw new  RuntimeException("Error updating student : " + e.getMessage());
        }
    }

    @Override
    public boolean updateStudent(String name, int age, Long id) {

        validateStudent(name, age, id);

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
        ){
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setLong(3, id);

            int rows = ps.executeUpdate();

            return rows > 0;

        }catch (SQLException e){
            throw new  RuntimeException("Error updating student : " + e.getMessage());
        }
    }

    @Override
    public boolean deleteStudent(Long id) {
        if(id <=0) throw new IllegalArgumentException("student id is less than 0");

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_SQL)
        ) {
            ps.setLong(1, id);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new  RuntimeException("Error deleting student : " + e.getMessage());
        }
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        int age = rs.getInt("age");

        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : null;

        return new Student(id, name, email, age, createdAt);
    }

    private void validateStudent(Student student, boolean requiredId) {
        if(requiredId){
            if(student.getId() == null || student.getId() <= 0) throw new IllegalArgumentException("student id is required");
        }

        if(student.getName() == null || student.getName().isBlank()) throw new IllegalArgumentException("student name is required");

        if(student.getAge() <= 0) throw new IllegalArgumentException("age must be > 0");
    }

    private void validateStudent(String name, Integer age , Long id) {

        if(id == null || id <= 0) throw new IllegalArgumentException("student id is required");

        if(name == null || name.isBlank()) throw new IllegalArgumentException("student name is required");

        if(age <= 0) throw new IllegalArgumentException("age must be > 0");
    }


}
