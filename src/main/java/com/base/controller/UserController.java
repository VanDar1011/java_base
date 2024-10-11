package com.base.controller;

import com.base.dto.PaginationRequest;
import com.base.entity.ResponseObject;
import com.base.entity.User;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;
import com.base.model.ResponseStatus;
import com.base.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "4") int size,
                                                  @RequestParam(required = false) String name) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        if (name != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                    "FINDING" +
                            " " +
                            "SUCCESS", userService.getUserByName(name)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                "FINDING SUCCESS",
                userService.getAll(paginationRequest)));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                "CREATE " +
                        "SUCCESS", userService.createUser(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") int id) {
        // xong
        User user = userService.getUserById(id);

        // Nếu tìm thấy người dùng, trả về kết quả
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Finding by ID success", user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable("id") int id,
                                                     @RequestBody User userDetails) {
        User updatedUser = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Update By Id success",
                        userService.updateUser(id,
                                userDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") int id) {
        boolean value_user = userService.deleteUser(id);
        if (value_user) return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Delete Success", null));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "User" +
                " not found", null));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                        ex.getMessage(), null
                ));

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseObject> handleConflictException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                        "Cannot delete user because it is referenced by other entities", null));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseObject> handleConflictException(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                        ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("error", "An error occurred", ex.getMessage()));
    }
}
