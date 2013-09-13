package com.shared.rides.service;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/dispatcher-servlet.xml" })
public class LoginServiceTest{

	@Autowired
	private LoginService loginService;

	@Test
	public void testValidate() {
		String email = "leandro.bagur@hotmail.com";
		String password = "leandro";
		HttpServletRequest r = Mockito.mock(HttpServletRequest.class);
		
		boolean resultado = loginService.validate(email, password, r);
		
		assertTrue(resultado);
	}

}
