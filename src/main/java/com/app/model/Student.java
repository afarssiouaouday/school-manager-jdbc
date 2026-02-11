package com.app.model;

import java.time.LocalDateTime;

public class Student {

    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDateTime created_at;

    public Student() {}

    public Student(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Student(Long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Student(Long id, String name, String email, int age, LocalDateTime created_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.created_at = created_at;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public LocalDateTime getCreated_at() {return created_at;}
    public void setCreated_at(LocalDateTime created_at) {this.created_at = created_at;}

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", email=" + email + ", age=" + age + ", created_at=" + created_at + "]";
    }
}
