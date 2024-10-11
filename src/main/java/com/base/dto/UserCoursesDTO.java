package com.base.dto;

public class UserCoursesDTO {
    private int id;
    private UserDTO user;
    private CourseDTO course;

    public UserCoursesDTO() {
    }

    public UserCoursesDTO(int id, UserDTO user, CourseDTO course) {
        this.id = id;
        this.user = user;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }
}
