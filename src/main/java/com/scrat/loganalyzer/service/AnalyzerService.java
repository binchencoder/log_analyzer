package com.scrat.loganalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrat.loganalyzer.dao.UserDao;
import com.scrat.loganalyzer.model.User;
@Service
public class AnalyzerService {
	@Autowired
	private UserDao userDao;
	public User getUser(String username) {
		try {
			User user = userDao.getUser(username);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean login(String username, String password) {
		try {
			User user = userDao.getUser(username);
			return user.getPassword().equals(password) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
