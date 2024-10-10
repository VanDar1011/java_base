package com.base.controller;

import com.base.dto.PaginationRequest;
import com.base.entity.Course;
import com.base.entity.ResponseObject;
import com.base.entity.User;
import com.base.service.implement.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1" +
        "/courses")
public class CoursesController {
    @Autowired
    CoursesService coursesService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "4") int size) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "FINDING SUCCESS", coursesService.getAll(paginationRequest)));

    }

    @PostMapping
    ResponseEntity<ResponseObject> createUser(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "CREATE SUCCESS", coursesService.createCourse(course)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") int id) {
        Course course =
                coursesService.getCourseById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "Courses not found", null));
        }

        // Nếu tìm thấy người dùng, trả về kết quả
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Finding by ID success", course));
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable("id") int id, @RequestBody Course courseDetails) {
        Course updatedCourse =
                coursesService.getCourseById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (updatedCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "Course not found", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Update By Id success", coursesService.updateCourse(id, courseDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") int id) {
        try {
            boolean value_user =
                    coursesService.deleteCourse(id);
            if (value_user)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Delete Success", null));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "Coures not found", null));
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
