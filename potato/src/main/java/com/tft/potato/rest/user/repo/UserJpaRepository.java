package com.tft.potato.rest.user.repo;

import com.tft.potato.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    String findUserIdByEmail(String email);
}
