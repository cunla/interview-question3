package com.example.demo.service;

import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.shared.TestData;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * ConversationService unit tests
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConversationServiceUnitTest {

    /**
     * Conversation Service injected
     */
    @Autowired
    ConversationService conversationService;

    /**
     * Question Repository mock
     */
    @MockBean
    QuestionRepository questionRepository;

    /**
     * Reply Repository mock
     */
    @MockBean
    ReplyRepository replyRepository;


    /**
     * Setup method to configure ConversationService before tests
     */
    @Before
    public void Setup() {
        ((ConversationServiceImpl)this.conversationService).setQuestionRepository(questionRepository);
        ((ConversationServiceImpl)this.conversationService).setReplyRepository(replyRepository);
    }

    /**
     * Tests successful execution scenario of ConversationService.getAllQuestions() 
     */
    @Test
    public void getAllQuestions_success() {
        //prepare
        val question = TestData.createTestQuestion();
        //Mock respository output
        when(questionRepository.findAll()).thenReturn(new ArrayList<Question>() {{add(question);}});

        //execute & collect
        val result = conversationService.listQuestions();

        //verify
        assertNotNull("Not Null Result", result);
        assertFalse("Not empty list", result.isEmpty());

    }

    /**
     * Tests failure scenario when questionRepository throws DataAccessException during execution of ConversationService.getAllQuestions()
     */
    @Test(expected = DataAccessException.class)
    public void getAllQuestions_fails_when_repo_fails() {
        //prepare
        val question = TestData.createTestQuestion();
        //Mock conversationService listQuestions
        when(questionRepository.findAll()).thenThrow(new DataRetrievalFailureException("generated"));


        //execute
        conversationService.listQuestions();

        //verify expected exception
    }


    /**
     * Tests successful execution scenario of ConversationService.getOneQuestion() 
     */
    @Test
    public void getOneQuestion_success() {
        //prepare
        val question = TestData.createTestQuestion();
        val optionalQuestion = Optional.of(question);

        //Mock respository output
        when(questionRepository.findById(anyLong())).thenReturn(optionalQuestion);

        //execute & collect
        val result = conversationService.findQuestion(1);

        //verify
        assertNotNull("Not Null Result", result);
        assertEquals("Author equals", question.getAuthor(), result.getAuthor());
        assertEquals("Message equals", question.getMessage(), result.getMessage());
    }




    /**
     * Tests failure scenario when questionRepository returns empty result during execution of ConversationService.getOneQuestion()
     */
    @Test(expected = RecordNotFoundException.class)
    public void getOneQuestion_fails_when_no_record() {
        //prepare
        Optional<Question> optionalQuestion = Optional.empty();

        //Mock respository output
        when(questionRepository.findById(anyLong())).thenReturn(optionalQuestion);

        //execute
        conversationService.findQuestion(1);

        //verify expected exception
    }

    /**
     * Tests failure scenario when questionRepository throws DataAccessException during execution of ConversationService.getOneQuestion()
     */
    @Test(expected = DataAccessException.class)
    public void getOneQuestion_fails_when_repo_fails() {
        //prepare

        //Mock conversationService findById
        when(questionRepository.findById(anyLong())).thenThrow(new DataRetrievalFailureException("generated"));


        //execute
        conversationService.findQuestion(1);

        //verify expected exception
    }

    /**
     * Tests successful execution scenario of ConversationService.createQuestion() 
     */
    @Test
    public void createQuestion_success() {
        //prepare
        val question = TestData.createTestQuestion();
        question.getReplies().clear();
        val message = new MessageDto(TestData.Q_AUTHOR, TestData.Q_MESSAGE);
        //Mock conversationService createQuestion
        when(questionRepository.saveAndFlush(any(Question.class))).thenReturn(question);

        //execute & collect
        val result = conversationService.createQuestion(message);

        //verify
        assertNotNull("Not Null Result", result);
        assertEquals("Author equals", question.getAuthor(), result.getAuthor());
        assertEquals("Message equals", question.getMessage(), result.getMessage());
    }

    /**
     * Tests failure scenario when questionRepository throws DataAccessException during execution of ConversationService.createQuestion()
     */
    @Test(expected = DataAccessException.class)
    public void createQuestion_fails_when_repo_fails() {
        //prepare
        val question = TestData.createTestQuestion();
        question.getReplies().clear();
        val message = new MessageDto(TestData.Q_AUTHOR, TestData.Q_MESSAGE);
        //Mock conversationService createQuestion
        when(questionRepository.saveAndFlush(any(Question.class))).thenThrow(new DataIntegrityViolationException("generated"));

        //execute
        conversationService.createQuestion(message);

        //verify expected exception
    }


    /**
     * Tests successful execution scenario of ConversationService.createReplyForQuestion() 
     */
    @Test
    public void createReplyForQuestion_success() {
        //prepare
        val question = TestData.createTestQuestion();
        val reply = question.getReplies().iterator().next();
        val message = new MessageDto(TestData.R_AUTHOR, TestData.R_MESSAGE);
        //Mock conversationService createQuestion
        when(replyRepository.saveAndFlush(any(Reply.class))).thenReturn(reply);

        //execute & collect
        val result = conversationService.createReplyForQuestion(question, message);

        //verify
        assertNotNull("Not Null Result", result);
        assertEquals("Author equals", message.getAuthor(), result.getAuthor());
        assertEquals("Message equals", message.getMessage(), result.getMessage());
    }

    /**
     * Tests failure scenario when questionRepository throws DataAccessException during execution of ConversationService.createReplyForQuestion()
     */
    @Test(expected = DataAccessException.class)
    public void createReplyForQuestion_fails_when_repo_fails() {
        //prepare
        val question = TestData.createTestQuestion();
        val reply = question.getReplies().iterator().next();
        val message = new MessageDto(TestData.R_AUTHOR, TestData.R_MESSAGE);
        //Mock conversationService createQuestion
        when(replyRepository.saveAndFlush(any(Reply.class))).thenThrow(new DataIntegrityViolationException("generated"));

        //execute
        conversationService.createReplyForQuestion(question, message);

        //verify expected exception
    }
}

