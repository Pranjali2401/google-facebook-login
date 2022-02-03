package com.isystango.social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isystango.social.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{

	User findByEmail(String email);

	User findByEmailAndPassword(String email,String password);

}
