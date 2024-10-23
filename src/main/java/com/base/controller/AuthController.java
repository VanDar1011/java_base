package com.base.controller;

import com.base.entity.ResponseObject;
import com.base.entity.User;
import com.base.repositories.UserRepository;
import com.base.utils.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("api/v1/login")
    public ResponseEntity<ResponseObject> login(@RequestBody User user) {
        List<User> extingUser = userRepository.findByName(user.getName());
        if (extingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "User" + " not found", null));
        }
        boolean checkPass = passwordEncoder.matches(user.getPassword(),
               extingUser.get(0).getPassword());
        if (!checkPass) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(ResponseStatus.FAIL.getStatus(), "Password Error", null));
        }
        user.setId(extingUser.get(0).getId());
        user.setProfile(extingUser.get(0).getProfile());
        user.setRoles(extingUser.get(0).getRoles());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(ResponseStatus.OK.getStatus(), "FINDING SUCCESS", user));

    }
}
