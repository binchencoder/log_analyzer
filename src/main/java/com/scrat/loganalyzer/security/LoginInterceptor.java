package com.scrat.loganalyzer.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.scrat.loganalyzer.model.User;
import com.scrat.loganalyzer.util.ResponseJSONUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String token = request.getParameter("token");
		User user = (User) session.getAttribute(token);
		if (user != null) {
			return super.preHandle(request, response, handler);
		}
		ResponseJSONUtil.response(response, 500, "\"请登录\"");
		return false;
	}
}
