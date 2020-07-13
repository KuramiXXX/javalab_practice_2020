package ru.itis.models;

/**
 * 10.07.2020
 * 01. Database
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class Mentor {
    private Long id;
    private String firstName;
    private String lastName;
    private Student student;

    public Mentor(Long id, String firstName, String lastName, Student student) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.student = student;
    }
}
