package com.scrat.loganalyzer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scrat.loganalyzer.model.User;
import com.scrat.loganalyzer.service.AnalyzerService;
import com.scrat.loganalyzer.util.MD5Util;
import com.scrat.loganalyzer.util.ResponseJSONUtil;

@Controller
public class BasicController {
	@Autowired
	private AnalyzerService analyzerService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) throws IOException{
		if (analyzerService.login(username, password)) {
			String currentTimestamp = String.valueOf(System.currentTimeMillis());
			StringBuffer tokenKey = new StringBuffer();
			tokenKey.append(username).append(currentTimestamp);
			String token = MD5Util.encoder(tokenKey.toString());
			User user = analyzerService.getUser(username);
			request.getSession().setAttribute(token, user);
			ResponseJSONUtil.response(response, 200, "\"登录成功\"");
		} else {
			ResponseJSONUtil.response(response, 417, "\"登录失败\"");
		}
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response, String username, String token) throws IOException {
		User user = analyzerService.getUser(username);
		if (user != null) {
			request.getSession().removeAttribute(token);
		}
		ResponseJSONUtil.response(response, 200, "\"退出成功\"");
	}
	
}
