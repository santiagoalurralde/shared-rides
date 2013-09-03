package com.shared.rides.service;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mockito;

import junit.framework.TestCase;

public class LoginServiceTest extends TestCase {


	public void testValidate() {
		String email = "leandro.bagur@hotmail.com";
		String password = "leandro";
		HttpServletRequest r = Mockito.mock(HttpServletRequest.class);
		
		LoginService loginService = Mockito.mock(LoginService.class);
		boolean resultado = loginService.validate(email, password, r);
		
		assertTrue(resultado);
	}

}
