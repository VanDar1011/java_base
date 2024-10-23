package com.base.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity()
@Table(name = "user_courses")
public class UserCourses implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_course", referencedColumnName = "id")
    private Course course;

    public UserCourses() {
    }

    public UserCourses(int id, User user, Course course) {
        this.id = id;
        this.user = user;
        this.course = course;
    }

    public UserCourses(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "UserCourses{" +
                "id=" + id +
                ", user=" + user +
                ", course=" + course +
                '}';
    }
}
