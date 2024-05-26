package com.example.springjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springjwt.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer>{

	User findByEmail(String email);
}
