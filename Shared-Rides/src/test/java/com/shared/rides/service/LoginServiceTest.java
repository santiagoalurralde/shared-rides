package com.shared.rides.service;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * Test que sirve para verificar el funcionamiento del servicio LoginService
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/test-servlet.xml" })
public class LoginServiceTest{

	@Autowired
	private LoginService loginService;

	/*
	 * En este caso verificamos si el metodo validate () del servicio LoginService 
	 * funciona correctamente.
	 * Le pasamos como parametros:
	 * 		email = leandro.bagur@hotmail.com
	 * 		password = leandro
	 * lo que se espera como respuesta es un True ya que ese usuario con esa pw existen
	 */
	@Test
	public void testValidate() {
		String email = "leandro.bagur@hotmail.com";
		String password = "leandro";
		HttpServletRequest r = new MockHttpServletRequest();
		//Hago esto asi crea una sesion automaticamente
		r.getSession();
		assertTrue(loginService.validate(email, password, r));
	}
	
}
