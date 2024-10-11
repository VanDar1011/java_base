package com.base.controller;

import com.base.dto.PaginationRequest;
import com.base.entity.ResponseObject;
import com.base.entity.User;
import com.base.model.ResponseStatus;
import com.base.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    ResponseEntity<ResponseObject> findAll(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "4")int size,
    @RequestParam(required = false) String name) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        if(name != null) {
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
    ResponseEntity<ResponseObject> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(ResponseStatus.OK.getStatus(),
                "CREATE " +
                "SUCCESS", userService.createUser(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "User not found", null));
        }

        // Nếu tìm thấy người dùng, trả về kết quả
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Finding by ID success", user));
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable("id") int id, @RequestBody User userDetails) {
        User updatedUser = userService.getUserById(id);

        // Kiểm tra nếu không tìm thấy người dùng
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "User not found", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Update By Id success", userService.updateUser(id, userDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") int id) {
        try {
            boolean value_user = userService.deleteUser(id);
            if (value_user) return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(ResponseStatus.OK.getStatus(), "Delete Success", null));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "User not found", null));
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject(ResponseStatus.FAIL.getStatus(),
                            "Cannot delete user because it is referenced by other entities", null));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "An error occurred", e.getMessage()));
        }
    }
}
