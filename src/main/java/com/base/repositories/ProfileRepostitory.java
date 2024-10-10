package com.base.repositories;

import com.base.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepostitory  extends JpaRepository<Profile, Integer> {
}
