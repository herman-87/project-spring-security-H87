package com.herman87.springsecurityH87.repository;

import com.herman87.springsecurityH87.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
