package com.example.demo.integrationtest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.DemoApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = DemoApplication.class, 
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
		)


@AutoConfigureMockMvc
/**
 * This is Integration testing to check with Database connection as well as service layer connectivity.
 * if Database is not reachable ( based on properties application.yaml) then it will failed
 * 
 * @author urmil
 *
 */
public class QuestionContollerIntegrationTest {
	
	@Autowired
	MockMvc mockMvc;

	/**
	 * it will perform test connectivity as well as it will create schema 
	 * @throws Exception
	 */
	@Test
	public void testRetrieveQuestions() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.get("/questionsxxxx")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andReturn();
	}
}