package com.base.dto;

import com.base.entity.Profile;
import com.base.entity.User;
import com.base.enums.Role;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDTO {
    private int id;
    private String name;
    private Set<String> roles;
    private Profile profile;
    public UserDTO convertUsertoUserDTO(User user){
    return new UserDTO(user.getId(), user.getName(), user.getRoles(),user.getProfile());
    }


}
