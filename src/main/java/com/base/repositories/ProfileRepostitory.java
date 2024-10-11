package com.base.repositories;

import com.base.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepostitory  extends JpaRepository<Profile, Integer> {
}
