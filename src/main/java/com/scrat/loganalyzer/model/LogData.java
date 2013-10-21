package com.scrat.loganalyzer.model;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class LogData {
	private String ip;
	private long ipSum;
	private Date date;
	private String api;
	private int code;
	private float requestLength;
	private int size;
	private String method;
	private String fileType;
	private String os;
	private String browser;

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the ipSum
	 */
	public long getIpSum() {
		return ipSum;
	}

	/**
	 * @param ipSum
	 *            the ipSum to set
	 */
	public void setIpSum(long ipSum) {
		this.ipSum = ipSum;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the api
	 */
	public String getApi() {
		return api;
	}

	/**
	 * @param api
	 *            the api to set
	 */
	public void setApi(String api) {
		this.api = api;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the requestLength
	 */
	public float getRequestLength() {
		return requestLength;
	}

	/**
	 * @param requestLength
	 *            the requestLength to set
	 */
	public void setRequestLength(float requestLength) {
		this.requestLength = requestLength;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os
	 *            the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser
	 *            the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("{\"ip\":\"").append(ip)
				.append("\",\"ipSum\":").append(ipSum).append(",\"date\":\"");
		if (date != null) {
			stringBuffer.append(DateFormatUtils.format(date,
					"yyyy-MM-dd HH:mm:ss"));
		} else {
			stringBuffer.append("");
		}
		stringBuffer.append("\",\"api\":\"").append(api).append("\",\"code\":")
				.append(code).append(",\"method\":\"").append(method)
				.append("\",\"requestLength\":").append(requestLength)
				.append(",\"size\":").append(size).append(",\"fileType\":\"")
				.append(fileType).append("\",\"os\":\"").append(os)
				.append("\",\"browser\":\"").append(browser).append("\"}");
		return stringBuffer.toString();
	}

	public static void main(String[] args) {
		System.out.println(new LogData());
	}
}
