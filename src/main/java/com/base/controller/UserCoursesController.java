package com.base.controller;

import com.base.dto.PaginationRequest;
import com.base.entity.Course;
import com.base.entity.ResponseObject;
import com.base.entity.User;
import com.base.entity.UserCourses;
import com.base.exception.NotFoundException;
import com.base.model.ResponseStatus;
import com.base.repositories.UserCoursesRepository;
import com.base.service.implement.CoursesService;
import com.base.service.implement.UserCoursesService;
import com.base.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/book-courses")
public class UserCoursesController {
    @Autowired
    UserCoursesService userCoursesService;
    @Autowired
    UserService userService;
    @Autowired
    private CoursesService coursesService;
    @Autowired
    private UserCoursesRepository userCoursesRepository;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findAll(@RequestParam(required = false) Integer idUser, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "4") int size) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        if (idUser != null) {
            User existingUser = userService.getUserById(idUser);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                        "UserCourse NOT FOUND", null));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                    "FINDING " +
                            "BY ID SUCCESS", userCoursesService.getCoursesByIdUser(idUser, paginationRequest)));

        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                    "FINDING " +
                            "SUCCESS", userCoursesService.getAll(paginationRequest)));

        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> create(@RequestBody Map<String, Integer> request, HttpMethod httpMethod) {
        int userId = request.get(
                "userId");
        int courseId = request.get(
                "courseId");
        System.out.println("userId : " +
                +userId);
        System.out.println("courseId " +
                ": " + courseId);
        User user = userService.getUserById(userId);
        // check if user not found
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Course course =
                coursesService.getCourseById(courseId);
        // check if courses not found
        if (course == null) {
            throw new NotFoundException("Course not found");
        }
        // check if courses assgined
        // to user
        UserCourses userCourses =
                userCoursesRepository.findByUserIdAndCourseId(userId, courseId);
        if (userCourses != null) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "Course already assigned to User", null));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(ResponseStatus.OK.getStatus(), "CREATE SUCCESS", userCoursesService.createUserCourse(userId, courseId)));
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable("id") int id, @RequestBody UserCourses userCoursesDetails) {
        UserCourses updatedUserCourse =
                userCoursesService.getCoursesById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (updatedUserCourse == null) {
            throw new NotFoundException("UserCourse not found");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Update By Id success",
                        userCoursesService.updateUserCourse(id, userCoursesDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") int id) {
//        try {
        boolean value_user =
                userCoursesService.deleteUserCourseById(id);
        if (!value_user)
            throw new NotFoundException("UserCourse not found");

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Delete Success",
                        null));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                        ex.getMessage(), null
                ));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("error", "An error occurred", ex.getMessage()));
    }


}
