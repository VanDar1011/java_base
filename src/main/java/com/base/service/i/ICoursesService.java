package com.base.service.i;

import com.base.dto.PaginatedResponse;
import com.base.dto.PaginationRequest;
import com.base.entity.Course;

import java.util.List;

public interface ICoursesService {
    public PaginatedResponse<Course> getAll(PaginationRequest paginationRequest);


    public Course getCourseById(int id);

    public List<Course> getCourseByName(String courseName);

    public Course getByExactlyName(String courseName);

    public Course createCourse(Course course);

    public Course updateCourse(int id,
                               Course courseDetails);

    public Boolean deleteCourse(int id);


}
