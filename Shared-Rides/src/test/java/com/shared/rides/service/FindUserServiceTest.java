package com.shared.rides.service;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/test-servlet.xml" })
public class FindUserServiceTest {

		@Autowired
		private FindUserService findUserService;
		/*
		 * Test para comprobar el funcionamiento del metodo findUsers del servicio
		 * FindUserService. Como parametros de busqueda tenemos:
		 * shift --> 2 (Turno tarde)
		 * profile --> 1 (Perfil Peaton)
		 * latitude --> -34.40013
		 * longitude --> 190.001
		 * Como resultado debo obtener el usuario Leandro Bagur que es peaton, tiene
		 * turno tarde y vive en el marker lat=108, long=190
		 */
		
		@Test
		public void findUsersTest(){
			int shift = 2;
			int profile = 1;
			String marker = "{lan: -34.231312 , lon: -61.123342}";
		}
}
