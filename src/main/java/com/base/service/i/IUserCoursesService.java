package com.base.service.i;

import com.base.dto.PaginatedResponse;
import com.base.dto.PaginationRequest;
import com.base.entity.User;
import com.base.entity.UserCourses;

import java.util.List;

public interface IUserCoursesService {
    public PaginatedResponse<UserCourses> getAll(PaginationRequest paginationRequest);

    public List<UserCourses> getCoursesByIdUser(int userId, PaginationRequest paginationRequest);

    public UserCourses createUserCourse(UserCourses userCourses);

    public UserCourses updateUserCourse(int id, UserCourses userCoursesDetails);

    public UserCourses getCoursesById(int userId);

    public Boolean deleteUserCourseById(int id);

    public Boolean deleteUserCourseByUserId(int userId);
}
