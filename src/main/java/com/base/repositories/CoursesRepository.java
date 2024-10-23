package com.base.repositories;

import com.base.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CoursesRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE  c.name like %?1%")
    List<Course> findByName(String name);
    @Query("SELECT c FROM Course c WHERE c.name = :name")
    Course findByExactlyName(String name);
}
