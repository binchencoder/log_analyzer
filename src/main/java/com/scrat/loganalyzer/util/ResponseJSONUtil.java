package com.scrat.loganalyzer.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseJSONUtil {
	public static void response(HttpServletResponse response, int code, String data) throws IOException{
		StringBuffer sb = new StringBuffer();
		sb.append("{\"code\":").append(code).append(",\"data\":").append(data).append("}");
		PrintWriter out = null;
		try{
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/json;charset=utf-8"); 
	        out = response.getWriter();
	        out.write(sb.toString());
	        out.flush();
		} finally {
			  out.close();
		}
	}
}
