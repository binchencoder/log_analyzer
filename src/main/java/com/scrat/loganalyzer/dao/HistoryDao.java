package com.scrat.loganalyzer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.scrat.loganalyzer.model.History;
@Repository
public class HistoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<History> historyMapper = new RowMapper<History>() {
		@Override
		public History mapRow(ResultSet rs, int arg1) throws SQLException {
			History history = new History();
			history.setPath(rs.getString("path"));
			history.setMd5Code(rs.getString("md5_code"));
			history.setStatus(rs.getString("status"));
			history.setTotalLine(rs.getInt("total_line"));
			history.setDt(rs.getTimestamp("dt"));
			return history;
		}
	};
	
	public History getHistory(String path) {
		String sql = "select dt,md5_code,path,status,total_line from history where path=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{path}, historyMapper);
	}
	
}
