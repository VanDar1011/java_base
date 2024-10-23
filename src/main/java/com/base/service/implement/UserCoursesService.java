package com.base.service.implement;

import com.base.dto.PaginatedResponse;
import com.base.dto.PaginationRequest;
import com.base.entity.Course;
import com.base.entity.User;
import com.base.entity.UserCourses;
import com.base.repositories.CoursesRepository;
import com.base.repositories.UserCoursesRepository;
import com.base.repositories.UserRepository;
import com.base.service.i.IUserCoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCoursesService implements IUserCoursesService {

    private final UserCoursesRepository userCoursesRepository;

    private final UserRepository userRepository;

    private final CoursesRepository coursesRepository;

    @Override
    public PaginatedResponse<UserCourses> getAll(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<UserCourses> userCoursesPage = userCoursesRepository.findAll(pageable);

        System.out.println("User course : " + userCoursesPage.getContent());

        // Trả về danh sách các UserCourses từ Page
        return new PaginatedResponse<>(userCoursesPage.getContent(),
                userCoursesPage.getTotalElements());
    }

    @Override
    public List<UserCourses> getCoursesByIdUser(int userId, PaginationRequest paginationRequest) {
        List<UserCourses> list = userCoursesRepository.findByUserId(userId);
        System.out.println("List : " + list);

        return list;
    }

    @Override
    public Boolean deleteUserCourseByUserId(int userId) {
        List<UserCourses> existingUserCourses = userCoursesRepository.findByUserId(userId);
        if (existingUserCourses.isEmpty()) {
            userCoursesRepository.deleteByUserId(userId);
            return true;
        }
        return false;
    }

    @Override
    public UserCourses getCoursesById(int id) {
        return userCoursesRepository.findById(id).orElse(null);
    }

    @Override
    public UserCourses createUserCourse(int userId, int courseId) {
        User existingUser = userRepository.findById(userId).orElse(null);
        Course existingCourse = coursesRepository.findById(courseId).orElse(null);
        UserCourses userCourses = new UserCourses(existingUser, existingCourse);
        return userCoursesRepository.save(userCourses);
    }

    @Override
    public UserCourses updateUserCourse(int id, UserCourses userCoursesDetails) {
        return null;

    }

    @Override
    public Boolean deleteUserCourseById(int id) {
        if (userCoursesRepository.existsById(id)) {
            userCoursesRepository.deleteById(id);
            return true; // Xóa thành công
        } else {
            return false; // Không
            // tìm thay courses
        }
    }
}
