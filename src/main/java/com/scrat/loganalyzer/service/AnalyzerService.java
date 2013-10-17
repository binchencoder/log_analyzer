package com.scrat.loganalyzer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private static final int HOST = 0;
	private static final int TIME2 = 2;
	
	private static final HashMap<String, Integer> PARSE_PARAM = new HashMap<String, Integer>();
	static {
		PARSE_PARAM.put("%host", HOST);
		PARSE_PARAM.put("%time2", TIME2);
	}
	
	public static Date parseTime2(String log, int startIndex){
		String timeStr = log.substring(startIndex, 19);
		System.out.println(timeStr);
		return null;
	}
	
	public static void test() {
//		2007-05-31 18:19:33 216.104.143.32 GET /some-page/ 200 35384 1 "http://www.example.com/nice_page.htm" "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; FunWebProducts; .NET CLR 1.1.4322; .NET CLR 2.0.50727)" "-"
//		Log format definition:
//		LogFormat = "%time2 %host %method %url %code %bytesd %other %refererquot %uaquot %otherquot"
		String log = "2007-05-31 18:19:33 216.104.143.32 GET /some-page/ 200 35384 1 \"http://www.example.com/nice_page.htm\" \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; FunWebProducts; .NET CLR 1.1.4322; .NET CLR 2.0.50727)\" \"-\"";
		String LogFormat = "%time2 %host %method %url %code %bytesd %other %refererquot %uaquot %otherquot";
		Matcher matcher = Pattern.compile("%\\S+").matcher(LogFormat);
		while (matcher.find()) {
			String param = matcher.group();
			if (PARSE_PARAM.get(param) == null){
				// 没有找到时候的解析规则去解析日志
				break;
			}
			switch (PARSE_PARAM.get(param)) {
			case 2:
				parseTime2(log, 0);
				break;
			default:
				break;
			}
			System.out.println(log);
		}
		
	}
	public static void main(String[] args) {
		try {
			test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
