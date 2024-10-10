package com.base.service.implement;

import com.base.dto.PaginatedResponse;
import com.base.dto.PaginationRequest;
import com.base.entity.Course;
import com.base.entity.User;
import com.base.entity.UserCourses;
import com.base.repositories.CoursesRepository;
import com.base.service.i.ICoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CoursesService implements ICoursesService {
    @Autowired
    CoursesRepository coursesRepository;

    @Override
    public PaginatedResponse<Course> getAll(PaginationRequest paginationRequest) {
        Pageable pageable =
                PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<Course> userPage = coursesRepository.findAll(pageable);

        // Trả về danh sách các UserCourses từ Page
        return new PaginatedResponse<>(userPage.getContent(), userPage.getTotalElements());
    }

    @Override
    public Course createCourse(Course course) {
        return coursesRepository.save(course);
    }

    @Override
    public Course getCourseById(int id) {
        return coursesRepository.findById(id).orElse(null);
    }

    @Override
    public Course updateCourse(int id, Course courseDetails) {
        Course existingCourse = coursesRepository.findById(id).orElseThrow(null);
        if (existingCourse.getName() != null) {
            existingCourse.setName(courseDetails.getName());
        }
        return coursesRepository.save(existingCourse);
    }

    @Override
    public Boolean deleteCourse(int id) {
        if (coursesRepository.existsById(id)) {
            coursesRepository.deleteById(id);
            return true; // Xóa thành công
        } else {
            return false; // Không
            // tìm thay courses
        }
    }
}
