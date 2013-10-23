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
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.scrat.loganalyzer.model.LogData;

public class ParseService {
	private static final int OTHER = -1;
	private static final int IP = 0;
	private static final int TIME1=1;
	private static final int TIME2 = 2;
	private static final int METHOD = 3;
	private static final int API = 4;
	private static final int CODE = 5;
	private static final int REQUESTLENGTH = 6;
	private static final int SIZE = 7;
	private static final HashMap<String, Integer> PARSE_PARAM = new HashMap<String, Integer>();
	static {
		PARSE_PARAM.put("%other", OTHER);
		PARSE_PARAM.put("%ip", IP);
		PARSE_PARAM.put("%time1", TIME1);
		PARSE_PARAM.put("%time2", TIME2);
		PARSE_PARAM.put("%method", METHOD);
		PARSE_PARAM.put("%api", API);
		PARSE_PARAM.put("%code", CODE);
		PARSE_PARAM.put("%requestlength", REQUESTLENGTH);
		PARSE_PARAM.put("%size", SIZE);
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
		
		ipv4Integer = NumberUtils.toLong(ipParams[0])*16777216 
				+ NumberUtils.toLong(ipParams[1])*65536 
				+ NumberUtils.toLong(ipParams[2])*256 
				+ NumberUtils.toLong(ipParams[3]);
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
	
	public static LogData parseOneLog(String logFormat, String log) throws ParseException {
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
				String other = StringUtils.equals(endChar, "") ? log : StringUtils.substringBefore(log, endChar);
				log = StringUtils.substringAfter(log, other);
				break;
			case IP:
				String ip = StringUtils.equals(endChar, "") ? log : StringUtils.substringBefore(log, endChar);
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
				String parsePatterns = "yyyy-MM-dd HH:mm:ss";
				String dateStr = StringUtils.substring(log, 0, parsePatterns.length());
				Date time2 = DateUtils.parseDate(dateStr, parsePatterns);
				logData.setDate(time2);
				log = StringUtils.substring(log, parsePatterns.length());
				break;
			case METHOD:
				String method = StringUtils.equals(endChar, "") ? log : StringUtils.substringBefore(log, endChar);
				logData.setMethod(method);
				log = StringUtils.substringAfter(log, method);
				break;
			case API:
				String request = StringUtils.equals(endChar, "") ? log : StringUtils.substringBefore(log, endChar);
				String api = StringUtils.substringBefore(request, "?");
				logData.setApi(api);
				String fileType = StringUtils.substringAfter(api, ".");
				logData.setFileType(fileType);
				log = StringUtils.substringAfter(log, request);
				break;
			case CODE:
				String codeStr = StringUtils.equals(endChar, "") ? log : StringUtils.substringBefore(log, endChar);
				int code = NumberUtils.toInt(codeStr);
				logData.setCode(code);
				log = StringUtils.substringAfter(log, codeStr);
				break;
			case REQUESTLENGTH:
				String requestLengthStr = StringUtils.equals(endChar, "") ? log : StringUtils.substringBefore(log, endChar);
				float requestLength = NumberUtils.toFloat(requestLengthStr);
				logData.setRequestLength(requestLength);
				log = StringUtils.substringAfter(log, requestLengthStr);
				break;
			case SIZE:
				String sizeStr = StringUtils.equals(endChar, "") ? log : StringUtils.substringBefore(log, endChar);
				int size = NumberUtils.toInt(sizeStr);
				logData.setSize(size);
				log = StringUtils.substringAfter(log, sizeStr);
				break;
			default:
				break;
			}
		}
		return logData;
	}
	
	public static void main(String[] args) {
		try {
			String log = "127.0.0.1 - - [03/Jul/2013:00:00:10 +0800] \"POST /api.img?age%3D3&name%3Dscrat HTTP/1.1\" 200 21.003 4";
			String logFormat = "%ip - - %time1 \"%method %api %other\" %code %requestlength %size";
			parseOneLog(logFormat, log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
