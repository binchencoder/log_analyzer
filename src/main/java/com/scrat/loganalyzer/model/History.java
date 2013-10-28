package com.scrat.loganalyzer.model;

import java.util.Date;

public class History {
	private String path;
	private int totalLine;
	private String md5Code;
	private String status;
	private Date dt;
	private int size;

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the totalLine
	 */
	public int getTotalLine() {
		return totalLine;
	}

	/**
	 * @param totalLine
	 *            the totalLine to set
	 */
	public void setTotalLine(int totalLine) {
		this.totalLine = totalLine;
	}

	/**
	 * @return the md5Code
	 */
	public String getMd5Code() {
		return md5Code;
	}

	/**
	 * @param md5Code
	 *            the md5Code to set
	 */
	public void setMd5Code(String md5Code) {
		this.md5Code = md5Code;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dt
	 */
	public Date getDt() {
		return dt;
	}

	/**
	 * @param dt
	 *            the dt to set
	 */
	public void setDt(Date dt) {
		this.dt = dt;
	}
}
