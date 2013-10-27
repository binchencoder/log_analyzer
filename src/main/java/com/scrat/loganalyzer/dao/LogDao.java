package com.scrat.loganalyzer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.scrat.loganalyzer.model.LogData;
@Repository
public class LogDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void saveLog(LogData data){
		String sql = "insert ignore into log set ip=?,ip_sum=?,dt=?,code=?,request_length=?,size=?,api=?,method=?,file_type,os=?";
		jdbcTemplate.update(sql, data.getIp(),data.getIpSum(),data.getDate(),data.getCode(),data.getRequestLength(),data.getSize(),data.getApi(),data.getMethod(),data.getFileType(),data.getOs());
	}
}
