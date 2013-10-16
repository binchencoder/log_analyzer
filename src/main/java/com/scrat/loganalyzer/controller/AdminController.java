package com.scrat.loganalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "scrat";
	}
}
