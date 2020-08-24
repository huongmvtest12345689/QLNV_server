package com.dimageshare.hrm.repository;

import com.dimageshare.hrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
