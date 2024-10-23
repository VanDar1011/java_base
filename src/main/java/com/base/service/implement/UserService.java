package com.base.service.implement;

import com.base.dto.PaginatedResponse;
import com.base.dto.PaginationRequest;
import com.base.dto.UserDTO;
import com.base.entity.Profile;
import com.base.entity.User;
import com.base.exception.NotFoundException;
import com.base.repositories.ProfileRepostitory;
import com.base.repositories.UserRepository;
import com.base.service.i.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;


    private final ProfileRepostitory profileRepository;

    @PostAuthorize("hasRole('ADMIN')")
    @Override
    public PaginatedResponse<UserDTO> getAll(PaginationRequest paginationRequest) {
       log.info("In method getAll");
        Pageable pageable =
                PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserDTO> userDTOS = userPage.map(user -> UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .build());

        // Trả về danh sách các UserCourses từ Page
        return new PaginatedResponse<>(userDTOS.getContent(), userDTOS.getTotalElements());
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

//    @PostAuthorize("hasRole('ADMIN')")
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with ID " + id + " not" +
                " found"));
    }

    @Override
    public List<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }
    @PostAuthorize("returnObject.name == authentication.name")
    @Override
    public User updateUser(int id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(null);
        // Cập nhật thông tin
        if (userDetails.getName() != null) {
//            System.out.println("Vao Name");
            existingUser.setName(userDetails.getName());
        }
        if (userDetails.getProfile() != null) {
//            System.out.println("Vao Profile");
            Profile existingProfile = existingUser.getProfile();
            existingProfile.setAddress(userDetails.getProfile().getAddress());
            profileRepository.save(existingProfile);
        }
        System.out.println(existingUser.toString());
        // Lưu thông tin người dùng đã cập nhật
        return userRepository.save(existingUser);
    }

    @Override
    public Boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true; // Xóa thành công
        } else {
            return false; // Không tìm thấy người dùng
        }
    }

    @Override
    public User getMyInfor() {
       var context = SecurityContextHolder.getContext();
       String name = context.getAuthentication().getName();
      List<User> users =   userRepository.findByName(name);
      if(users.isEmpty()) {
          throw new NotFoundException("User with name " + name + " not found");
      }
      return users.get(0);
    }


}
