package com.base.repositories;


import com.base.entity.User;
import com.base.entity.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface UserCoursesRepository extends JpaRepository<UserCourses, Integer> {
    @Query("SELECT uc FROM " + "user_courses uc WHERE uc" + ".user.id = :userId")
    List<UserCourses> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM user_courses uc WHERE uc.user.id = :userId")
    boolean deleteByUserId(int userId);

    @Query("SELECT uc FROM " + "user_courses  uc WHERE " + "uc.user.id= :userId AND uc.course.id=:couresId")
    UserCourses findByUserIdAndCourseId(int userId, int couresId);

}
