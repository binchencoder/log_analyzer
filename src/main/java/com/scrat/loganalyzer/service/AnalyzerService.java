package com.scrat.loganalyzer.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
	private static final int OTHER = -1;
	private static final int IP = 0;
	private static final int TIME1=1;
	private static final int TIME2 = 2;
	
	private static final HashMap<String, Integer> PARSE_PARAM = new HashMap<String, Integer>();
	static {
		PARSE_PARAM.put("%other", OTHER);
		PARSE_PARAM.put("%ip", IP);
		PARSE_PARAM.put("%time1", TIME1);
		PARSE_PARAM.put("%time2", TIME2);
	}
	
	private static ThreadLocal<DateFormat> time1Thread = new ThreadLocal<DateFormat>() {
        protected synchronized DateFormat initialValue() {
            return new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        }
    };
    
    public static Date parseTime1(String timeStr) throws ParseException {
    	DateFormat df = time1Thread.get();
		Date result = df.parse(timeStr);
		return result;
	}
	
	public static Date parseTime2(String timeStr) throws ParseException{
		Date result = DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss");
		return result;
	}
	
	public static String parseHost(String log, int startIndex) {
		log = log.substring(startIndex);
		int hostLength = log.indexOf(" ");
		String host = log.substring(0, hostLength);
		return host;
	}
	
	public static int ipv4ToInt(String ipv4Str){
		int ipv4Integer = 0;
		String[] ipParams = StringUtils.split(ipv4Str, "\\.");
		if (ipParams.length != 4) {
			return ipv4Integer;
		}
		ipv4Integer = Integer.valueOf(ipParams[0])*16777216 + Integer.valueOf(ipParams[1])*65536 + Integer.valueOf(ipParams[2])*256 + Integer.valueOf(ipParams[3]);
		return ipv4Integer;
	}
	
	public static String intToIpv4(int ipv4Int) {
		String[] array = new String[4];
		array[0] = String.valueOf(( ipv4Int/16777216 ) % 256);
		array[1] = String.valueOf(( ipv4Int/65536     ) % 256);
		array[2] = String.valueOf(( ipv4Int/256 ) % 256);
		array[3] = String.valueOf(( ipv4Int ) % 256);
		String ipv4Str = StringUtils.join(array, ",");
		return ipv4Str;
	}
	
	public static void test() throws ParseException {
//		2007-05-31 18:19:33 216.104.143.32 GET /some-page/ 200 35384 1 "http://www.example.com/nice_page.htm" "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; FunWebProducts; .NET CLR 1.1.4322; .NET CLR 2.0.50727)" "-"
//		LogFormat = "%time2 %host %method %url %code %bytesd %other %refererquot %uaquot %otherquot"
		String log = "other 174.36.207.186 [03/May/2013:12:12:12 +0800] 2007-05-31 18:19:33 other";
		String logFormat = "%other %ip [%time1] %time2 %other";
		Matcher matcher = Pattern.compile("(%[\\w\\d]+)").matcher(logFormat);
		while (matcher.find()) {
			String param = matcher.group();
			int paramIndex = logFormat.indexOf(param);
			log = log.substring(paramIndex);
			logFormat = logFormat.substring(paramIndex);
			if (PARSE_PARAM.get(param) == null){
				// 没有找到时候的解析规则去解析日志
				break;
			}			
			switch (PARSE_PARAM.get(param)) {
			case OTHER:
				int otherLength = log.indexOf(" ");
				if (otherLength > -1) {
					log = log.substring(otherLength);
				}
				break;
			case IP:
				int ipLength = log.indexOf(" ");
				String ip = log;
				if (ipLength > -1) {
					ip = log.substring(0, ipLength);
					log = log.substring(ipLength);
				}
				int ipSum = ipv4ToInt(ip);
				System.out.println("ip="+ip+" sum="+ipSum);
				System.out.println(ipv4ToInt("2921648058"));
				break;
			case TIME1:
				Date date1 = parseTime1(log.substring(0, 26));
				System.out.println("date1="+date1);
				log = log.substring(26);
				break;
			case TIME2:
				Date date = parseTime2(log.substring(0, 19));
				System.out.println("date2="+date);
				log = log.substring(19);
				break;
			default:
				break;
			}
			logFormat = logFormat.substring(param.length());
		}
	}
	
	
	
	public static void main(String[] args) {
		try {
			test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
