package com.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")

public class Test {
    @GetMapping("/hello") // Ánh xạ HTTP GET với URL /api/hello
    public String sayHello() {
        return "Hello, World!";
    }
}
