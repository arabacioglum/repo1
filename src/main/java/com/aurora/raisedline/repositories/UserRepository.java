package com.aurora.raisedline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurora.raisedline.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByForgotPasswordCode(String forgotPasswordCode);

}
