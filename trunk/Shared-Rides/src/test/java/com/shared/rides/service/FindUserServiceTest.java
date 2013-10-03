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
		 * Test para comprobar el funcionamiento del metodo findUsersTest del servicio
		 * FindUserService. Como parametros de busqueda tenemos:
		 * shift --> 1 (Turno tarde)
		 * profile --> 0 (Perfil Peaton)
		 * latitude --> 108.001
		 * longitude --> 190.001
		 * Como resultado debo obtener el usuario Leandro Bagur que es peaton, tiene
		 * turno tarde y vive en el marker lat=108, long=190
		 */
		
		@Test
		public void findUsersTest(){
			int shift = 1;
			int profile = 0;
			double latitude = 108.001;
			double longitude = 190.001;	
			
			assertNotNull(findUserService.findUsers(profile, shift, longitude, latitude));
		}
}
