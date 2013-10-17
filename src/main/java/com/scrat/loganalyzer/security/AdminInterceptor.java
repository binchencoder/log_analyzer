package com.scrat.loganalyzer.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.scrat.loganalyzer.model.User;
import com.scrat.loganalyzer.util.ResponseJSONUtil;

@Repository
public class AdminInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getParameter("token");
		User user = (User) request.getSession().getAttribute(token);
		if (user != null && "1".equals(user.getStatus())) {
			return super.preHandle(request, response, handler);
		}
		ResponseJSONUtil.response(response, 401, "\"权限不足\"");
		return false;
	}
}
