package com.isystango.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isystango.social.dao.UserDao;
import com.isystango.social.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void setUser(User user) {
		userDao.save(user);
	}

	public User validateUser(String email, String password) {
		User userDetails = userDao.findByEmailAndPassword(email, password);
		return userDetails ;
	}
}
