package com.base.service.i;

import com.base.dto.PaginatedResponse;
import com.base.dto.PaginationRequest;
import com.base.entity.Course;
import com.base.entity.User;
import com.base.entity.UserCourses;

public interface ICoursesService {
    public PaginatedResponse<Course> getAll(PaginationRequest paginationRequest);

    public Course createCourse(Course course);

    public Course getCourseById(int id);

    public Course updateCourse(int id,
                               Course courseDetails);

    public Boolean deleteCourse(int id);
}
