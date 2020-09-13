package com.example.demo.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.service.QuestionService;
import com.example.demo.service.ReplyService;

@RunWith(SpringRunner.class)
//@SpringBootTest
@ComponentScan("com.example.demo.*")
@AutoConfigureTestDatabase
@WebMvcTest
public class QuestionServiceUnitTests {

	@Autowired
	private QuestionService questionService;

	@InjectMocks
	private ReplyService replyService;
	

	@MockBean
	private QuestionRepository questionRepository;
	
	@MockBean
	private ReplyRepository replyRepository;
	


	
	/**
	 * Testing QuestionService createOrUpdateQuestion method for question
	 * @throws Exception
	 */
	@Test
	void testQuestionCreation() throws Exception {

		Question q = new Question();
		q.setAuthor("Test Author");
		q.setMessage("Messsage"); // whichever data your entity class have

		//Mock find by Id
		Mockito.when(questionRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(q));

		//Mock save method
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(q);

		Question created = questionService.createOrUpdateQuestion(q);
		
		assertEquals(created.getAuthor(), q.getAuthor());
	}
	
	/**
	 * Testing QuestionService createOrUpdateQuestion method for question
	 * @throws Exception
	 */
	@Test
	void testAllGetQuestions() throws Exception {

		Question q = new Question();
		q.setAuthor("Test Author");
		q.setMessage("Messsage"); // whichever data your entity class have

		List<Question> list = new ArrayList<>();
		list.add(q);
		
		//Mock findAll
		Mockito.when(questionRepository.findAll()).thenReturn(list);

		List<Question> newList = questionService.getAllQuestions();
		
		//compare size of list
		assertEquals(newList.size(), 1);
	}

}
