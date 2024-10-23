package com.base.dto;

import com.base.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserWithToken {
    private String token;
    private User user;
}
