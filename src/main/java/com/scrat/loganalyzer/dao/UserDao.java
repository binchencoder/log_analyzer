package com.scrat.loganalyzer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.scrat.loganalyzer.model.User;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void delUser(int id) {
		String sql = "delete from users where id=?";
		jdbcTemplate.update(sql, id);
	}
	
	public void delUser(String username) {
		String sql = "delete from users where username=?";
		jdbcTemplate.update(sql, username);
	}
	
	public List<User> getUsers() {
		String sql = "select id,username,password,status from users";
		return jdbcTemplate.query(sql, userMapper);
	}
	
	public User getUser(String username) {
		String sql = "select id,username,password,status from users where username=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{username}, userMapper);
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			return user;
		}
	};
}
