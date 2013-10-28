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
			history.setSize(rs.getInt("size"));
			return history;
		}
	};
	
	public History getHistory(String path) {
		String sql = "select dt,md5_code,path,status,total_line,size from history where path=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{path}, historyMapper);
	}
	
	public void saveHistory(History history) {
		String sql = "insert ignore into history set dt=?,md5_code=?,path=?,status=?,total_line=?,size=?";
		jdbcTemplate.update(sql, history.getDt(),history.getMd5Code(),history.getPath(),history.getStatus(),history.getTotalLine(),history.getSize());
	}
	
	public void updateHistory(History history) {
		String sql = "update history set dt=?,md5_code=?,status=?,total_line=?,size=? where path=?";
		jdbcTemplate.update(sql, history.getDt(),history.getMd5Code(),history.getStatus(),history.getTotalLine(),history.getSize(),history.getPath());
	}
	
	public void deleteHistory(String path) {
		String sql = "delete from history where path=?";
		jdbcTemplate.update(sql, path);
	}
}
