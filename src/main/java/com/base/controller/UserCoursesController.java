package com.base.controller;

import com.base.dto.PaginationRequest;
import com.base.entity.ResponseObject;
import com.base.entity.User;
import com.base.entity.UserCourses;
import com.base.service.implement.UserCoursesService;
import com.base.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/book-courses")
public class UserCoursesController {
    @Autowired
    UserCoursesService userCoursesService;
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findAll(@RequestParam(required = false) Integer idUser, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "4") int size) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        if (idUser != null) {
            User existingUser = userService.getUserById(idUser);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("FAIL", "UserCourse NOT FOUND", null));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "FINDING BY ID SUCCESS", userCoursesService.getCoursesByIdUser(idUser, paginationRequest)));

        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "FINDING SUCCESS", userCoursesService.getAll(paginationRequest)));

        }
    }

    @PostMapping()
    public ResponseEntity<ResponseObject> create(@RequestBody UserCourses userCourses) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "CREATE SUCCESS", userCoursesService.createUserCourse(userCourses)));
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable("id") int id, @RequestBody UserCourses userCoursesDetails) {
        UserCourses updatedUserCourse =
                userCoursesService.getCoursesById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (updatedUserCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "User not found", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Update By Id success", userCoursesService.updateUserCourse(id, userCoursesDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") int id) {
        try {
            boolean value_user =
                    userCoursesService.deleteUserCourseById(id);
            if (value_user)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Delete Success", null));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "UserCourse not found", null));
        } catch (
                DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("fail",
                            "Cannot delete user because it is referenced by other entities", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "An error occurred", e.getMessage()));
        }
    }


}
