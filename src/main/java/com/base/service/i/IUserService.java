package com.base.service.i;

import com.base.dto.PaginatedResponse;
import com.base.dto.PaginationRequest;
import com.base.dto.UserDTO;
import com.base.entity.User;

import java.util.List;

public interface IUserService {
    public PaginatedResponse<UserDTO> getAll(PaginationRequest paginationRequest);

    public User getUserById(int id);

    public List<User> getUserByName(String name);

    public User createUser(User user);

    public User updateUser(int id, User userDetails);

    public Boolean deleteUser(int id);
    public User getMyInfor();
}
