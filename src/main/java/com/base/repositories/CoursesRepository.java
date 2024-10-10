package com.base.repositories;

import com.base.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Course, Integer> {
}
