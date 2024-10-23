package com.base.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.base.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u  FROM User u WHERE u.name " +
            "LIKE " +
            "%?1%")
    List<User> findByName(String name);


}
