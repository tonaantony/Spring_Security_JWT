package com.ust.Security.repository;

import com.ust.Security.model.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Userinforepository extends JpaRepository<Userinfo, Integer> {
    Optional<Userinfo> findByName(String username);
    Optional<Userinfo> findByEmail(String email);
    Optional<Userinfo> findByResetToken(String resetToken);
}
