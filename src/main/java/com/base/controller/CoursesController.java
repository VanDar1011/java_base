package com.base.controller;

import com.base.dto.PaginationRequest;
import com.base.entity.Course;
import com.base.entity.ResponseObject;
import com.base.model.ResponseStatus;
import com.base.service.implement.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// url path
@RestController
@RequestMapping(path = "/api/v1" +
        "/courses")
public class CoursesController {
    @Autowired
    CoursesService coursesService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "4") int size,
                                                  @RequestParam(required = false) String name) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        if (name != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                    "FINDING " +
                            "SUCCESS", coursesService.getCourseByName(name)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                "FINDING SUCCESS",
                coursesService.getAll(paginationRequest)));

    }

    @PostMapping
    ResponseEntity<ResponseObject> createUser(@RequestBody Course course) {
        Course course1 = coursesService.getByExactlyName(course.getName());
        if (course1 != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                    "COURSE ALREADY EXISTS", course1));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                "CREATE " +
                        "SUCCESS", coursesService.createCourse(course)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") int id) {
        Course course =
                coursesService.getCourseById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "Courses not found", null));
        }

        // Nếu tìm thấy người dùng, trả về kết quả
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Finding by ID success", course));
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable("id") int id, @RequestBody Course courseDetails) {
        Course updatedCourse =
                coursesService.getCourseById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (updatedCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "Course not found", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Update By Id success",
                        coursesService.updateCourse(id, courseDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") int id) {
        try {
            boolean value_user =
                    coursesService.deleteCourse(id);
            if (value_user)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Delete Success", null));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "Coures not found", null));
        } catch (
                DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                            "Cannot delete user because it is referenced by other entities", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "An error occurred", e.getMessage()));
        }
    }

}
