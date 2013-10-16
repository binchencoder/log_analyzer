package com.scrat.loganalyzer.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.scrat.loganalyzer.controller.BasicController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:log_analyzer-servlet.xml" })
public class TestBasicController {
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private BasicController basicController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(basicController).build();
	}
	
	@Test
	public void testLogin() throws Exception {
		ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.accept(MediaType.APPLICATION_JSON)
				.param("name", "name@email.com")
				.param("password", "pwd"));
		MvcResult mr = ra.andReturn();
		String result = mr.getResponse().getContentAsString();
		Assert.assertEquals("{\"code\":417,\"data\":\"登录失败\"}", result);
	}
	
	@Test
	public void testLogout() throws Exception {
		ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/logout").accept(MediaType.APPLICATION_JSON).param("username", "name").param("token", "tokenID"));
		MvcResult mr = ra.andReturn();
		String result = mr.getResponse().getContentAsString();
		Assert.assertEquals("{\"code\":200,\"data\":\"退出成功\"}", result);
	}
}
