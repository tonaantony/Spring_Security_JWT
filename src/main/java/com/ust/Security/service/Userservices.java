package com.ust.Security.service;


import com.ust.Security.model.Userinfo;
import com.ust.Security.repository.Userinforepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Userservices {
    @Autowired
    private Userinforepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String addUser(Userinfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repo.save(userInfo);
        return "user added to system ";
    }
    public void sendResetToken(String email) {
        Optional<Userinfo> optional = repo.findByEmail(email);
        Userinfo user;
        if(optional.isPresent()){
            user = optional.get();
        }
        else{
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 15 * 60 * 1000)); // Token valid for 15 minutes
        repo.save(user);

        // Send email (use any email service)
        sendResetEmail(email, resetToken);
    }

    private void sendResetEmail(String email, String resetToken) {
        // Logic to send email
        String resetLink = "http://localhost:2008/api/reset-password?token=" + resetToken;
        System.out.println("Password reset link: " + resetLink);
        // Use an email library like JavaMailSender to send this link
    }

    public void resetPassword(String token, String newPassword) {
        Optional<Userinfo> optional = repo.findByResetToken(token);
        Userinfo user;
        if(optional.isPresent()){
            user = optional.get();
        }
        else{
            throw new IllegalArgumentException("Invalid or expired reset token.");
        }

        // Update the user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Clear the token
        user.setResetTokenExpiry(null);
        repo.save(user);
    }
}

