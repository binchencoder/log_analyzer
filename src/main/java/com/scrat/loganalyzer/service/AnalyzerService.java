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
import com.scrat.loganalyzer.model.LogData;
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
	private static final int METHOD = 3;
	private static final int API = 4;
	private static final int CODE = 5;
	private static final HashMap<String, Integer> PARSE_PARAM = new HashMap<String, Integer>();
	static {
		PARSE_PARAM.put("%other", OTHER);
		PARSE_PARAM.put("%ip", IP);
		PARSE_PARAM.put("%time1", TIME1);
		PARSE_PARAM.put("%time2", TIME2);
		PARSE_PARAM.put("%method", METHOD);
		PARSE_PARAM.put("%api", API);
		PARSE_PARAM.put("%code", CODE);
	}
	
	private static ThreadLocal<DateFormat> time1Thread = new ThreadLocal<DateFormat>() {
        protected synchronized DateFormat initialValue() {
            return new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        }
    };
    
	public static long ipv4ToNum(String ipv4Str){
		long ipv4Integer = 0;
		String[] ipParams = StringUtils.split(ipv4Str, "\\.");
		if (ipParams.length != 4) {
			return ipv4Integer;
		}
		ipv4Integer = Long.valueOf(ipParams[0])*16777216 + Long.valueOf(ipParams[1])*65536 + Long.valueOf(ipParams[2])*256 + Long.valueOf(ipParams[3]);
		return ipv4Integer;
	}
	
	public static String numToIPV4(long ipv4Num) {
		String[] array = new String[4];
		array[0] = String.valueOf(( ipv4Num/16777216 ) % 256);
		array[1] = String.valueOf(( ipv4Num/65536     ) % 256);
		array[2] = String.valueOf(( ipv4Num/256 ) % 256);
		array[3] = String.valueOf(( ipv4Num ) % 256);
		String ipv4Str = StringUtils.join(array, ".");
		return ipv4Str;
	}
	
	public static void test() throws ParseException {
//		2007-05-31 18:19:33 216.104.143.32 GET /some-page/ 200 35384 1 "http://www.example.com/nice_page.htm" "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; FunWebProducts; .NET CLR 1.1.4322; .NET CLR 2.0.50727)" "-"
//		LogFormat = "%time2 %host %method %url %code %bytesd %other %refererquot %uaquot %otherquot"
		String log = "127.0.0.1 1.0.0.0 - - [03/Jul/2013:00:00:10 +0800] \"GET /api?age%3D3&name%3Dscrat";
		String logFormat = "%ip %ip - - [%time1] \"%method %api";
		log = "127.0.0.1 - - [03/Jul/2013:00:00:10 +0800] \"POST /api.img?age%3D3&name%3Dscrat HTTP/1.1\"";
		logFormat = "%ip %other - %time1 \"%method %api %other\"";
		Matcher matcher = Pattern.compile("(%[\\w\\d]+)").matcher(logFormat);
		LogData logData = new LogData();
		while (matcher.find()) {
			String param = matcher.group();
			int paramIndex = logFormat.indexOf(param);
			log = StringUtils.substring(log, paramIndex);
			logFormat = StringUtils.substringAfter(logFormat, param);
			String endChar = StringUtils.substring(logFormat, 0, 1);
			if (PARSE_PARAM.get(param) == null){
				// 没有找到时候的解析规则去解析日志
				break;
			}			
			switch (PARSE_PARAM.get(param)) {
			case OTHER:
				String other = StringUtils.substringBefore(log, endChar);
				log = StringUtils.substringAfter(log, other);
				System.out.println(other);
				break;
			case IP:
				String ip = StringUtils.substringBefore(log, endChar);
				logData.setIp(ip);
				long ipSum = ipv4ToNum(ip);
				logData.setIpSum(ipSum);
				log = StringUtils.substringAfter(log, ip);
				break;
			case TIME1:
				String time1Str = StringUtils.substringBetween(log, "[", "]");
				DateFormat df = time1Thread.get();
				Date time1 = df.parse(time1Str);
				logData.setDate(time1);
				log = StringUtils.substringAfter(log, time1Str);
				break;
			case TIME2:
				String dateStr = StringUtils.substring(log, 0, 19);
				Date time2 = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
				logData.setDate(time2);
				log = StringUtils.substring(log, 19);
				break;
			case METHOD:
				String method = StringUtils.substringBefore(log, endChar);
				logData.setMethod(method);
				log = StringUtils.substringAfter(log, method);
				break;
			case API:
				String request = StringUtils.substringBefore(log, endChar);
				String api = StringUtils.substringBefore(request, "?");
				logData.setApi(api);
				String fileType = StringUtils.substringAfter(api, ".");
				logData.setFileType(fileType);
				log = StringUtils.substringAfter(log, request);
				break;
			case CODE:
				break;
			default:
				break;
			}
		}
		System.out.println(logData);
	}
	
	public static void main(String[] args) {
		try {
			test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
