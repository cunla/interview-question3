package com.example.demo.unittest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.Question;
import com.example.demo.model.Reply;
import com.example.demo.service.QuestionService;
import com.example.demo.service.ReplyService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is class test QuestionContoller for all end point
 * @author urmil
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class QuestionControllerUnitTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionService questionService;

	@MockBean
	private ReplyService replyService;

	
	/**
	 * Testing Post method for question
	 * URL : "/questions"
	 * method: Post
	 * Model : Question
	 * it will send post request on Model Question
	 * @throws Exception
	 */
	@Test
	void testQuestionCreation() throws Exception {

		Question q = new Question();
		q.setAuthor("Test Author");
		q.setMessage("Messsage"); // whichever data your entity class have

		Mockito.when(questionService.createOrUpdateQuestion(Mockito.any(Question.class))).thenReturn(q);

		mockMvc.perform(MockMvcRequestBuilders.post("/questions").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(q))).andExpect(status().isOk());
	}

	/**
	 * Testing Post method for Reply
	 * URL : "/questions/{id}/reply"
	 * method: Post
	 * Model : Reply
	 * it will send post request on Model Reply
	 * @throws Exception
	 */
	@Test
	void testReplyCreation() throws Exception {

		Question q = new Question();
		q.setAuthor("Test Author");
		q.setMessage("Messsage"); // whichever data your entity class have
		q.setId(1L);

		Mockito.when(questionService.getQuestionById(1L)).thenReturn(q);

		Reply r = new Reply();
		r.setAuthor("Test Author");
		r.setMessage("Messsage"); // whichever data your entity class have
		r.setQuestion(q);

		Mockito.when(replyService.createReply(r, q.getId())).thenReturn(r);

		mockMvc.perform(MockMvcRequestBuilders.post("/questions/1/reply").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(r))).andExpect(status().isOk());

	}
	
	/**
	 * Testing All Questions
	 * URL : "/questions/{id}/reply"
	 * method: Post
	 * Model : Reply
	 * it will send post request on Model Reply
	 * @throws Exception
	 */
	@Test
	void testGetAllQuestion() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/questions")
					.contentType(MediaType.APPLICATION_JSON)
					).andExpect(status().isOk());
	}
	
	/**
	 * Testing one Questions
	 * URL : "/questions/{id}/reply"
	 * method: Post
	 * Model : Reply
	 * it will send post request on Model Reply
	 * @throws Exception
	 */
	@Test
	void testGetOneQuestion() throws Exception {
		
		//We are just checking it is not breaking end point.
		//We are not checking content
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/questions/1")
					.contentType(MediaType.APPLICATION_JSON)
					).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
