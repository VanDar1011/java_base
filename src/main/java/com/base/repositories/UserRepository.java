package com.base.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.base.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findAll();
}
