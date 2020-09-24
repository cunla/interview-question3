package com.example.demo.web;

import com.example.demo.shared.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Question;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.service.ConversationService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ConversationController unit tests
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ConversationController.class)
public class ConversationControllerUnitTest {

    /**
     * MockMvc injected
     */
    @Autowired
    private MockMvc mockMvc;


    /**
     * ConversationService mock
     */
    @MockBean
    ConversationService conversationService;


    /**
     * Tests successful execution scenario for GET operation to get list questions at http://localhost:{PORT}/questions
     * Expected response status is 200
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void getAllQuestions_200_when_all_good() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        //Mock conversationService listQuestions
        when(conversationService.listQuestions()).thenReturn(new ArrayList<Question>() {{add(question);}});


        //execute
        mockMvc.perform(get("/questions/"))
        //collect and verify
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].message").value(TestData.Q_MESSAGE))
                .andExpect(jsonPath("$.[0].author").value(TestData.Q_AUTHOR))
                .andExpect(jsonPath("$.[0].replies").exists())
                .andExpect(jsonPath("$.[0].replies").value(1))
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).listQuestions();
    }

    /**
     * Tests failure scenario for GET operation to get list questions at http://localhost:{PORT}/questions
     * The scenario is when conversationService throws DataAccessException originated in underlying repository
     * Expected response status is 500
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void getAllQuestions_500_when_svc_fails() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        //Mock conversationService listQuestions
        when(conversationService.listQuestions()).thenThrow(new DataRetrievalFailureException("generated"));


        //execute
        mockMvc.perform(get("/questions/"))
                //collect and verify
                .andExpect(status().isInternalServerError())
        ;
        //verify that Svc method called
        verify(conversationService, times(1)).listQuestions();
    }



    /**
     * Tests successful execution scenario for GET operation to get a question at http://localhost:{PORT}/questions/{questionId}
     * Expected response status is 200
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void getOneQuestion_200_when_all_good() throws Exception {
        //prepare
         val question = TestData.createTestQuestion();
        //Mock conversationService findQuestion
        when(conversationService.findQuestion(anyLong())).thenReturn(question);

        //execute
        mockMvc.perform(get("/questions/1/"))
        //collect and verify
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(TestData.Q_MESSAGE))
                .andExpect(jsonPath("$.author").value(TestData.Q_AUTHOR))
                .andExpect(jsonPath("$.replies").exists())
                .andExpect(jsonPath("$.replies").isArray())
                .andExpect(jsonPath("$.replies.[0].author").value(TestData.R_AUTHOR))
                .andExpect(jsonPath("$.replies.[0].message").value(TestData.R_MESSAGE))
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).findQuestion(1);
    }

    /**
     * Tests failure scenario for GET operation to get a question at http://localhost:{PORT}/questions/{questionId}
     * The scenario is when conversationService throws DataAccessException originated in underlying repository
     * Expected response status is 500
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void getOneQuestion_500_when_svc_fails() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        //Mock conversationService findQuestion
        when(conversationService.findQuestion(anyLong())).thenThrow(new DataRetrievalFailureException("generated"));

        //execute
        mockMvc.perform(get("/questions/1/"))
                //collect and verify
                .andExpect(status().isInternalServerError())
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).findQuestion(1);
    }

    /**
     * Tests failure scenario for GET operation to get a question at http://localhost:{PORT}/questions/{questionId}
     * The scenario is when conversationService throws RecordNotFoundException for questionId
     * Expected response status is 400
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void getOneQuestion_400_when_no_record() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        //Mock conversationService findQuestion
        when(conversationService.findQuestion(anyLong())).thenThrow(new RecordNotFoundException("generated"));

        //execute
        mockMvc.perform(get("/questions/1/"))
                //collect and verify
                .andExpect(status().isBadRequest())
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).findQuestion(1);
    }

    /**
     * Tests successful execution scenario for POST operation to create a question at http://localhost:{PORT}/questions
     * Expected response status is 201
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void createQuestion_201_when_all_good() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        question.getReplies().clear();
        final ObjectMapper mapper = new ObjectMapper();
        val message = new MessageDto(TestData.Q_AUTHOR, TestData.Q_MESSAGE);
        //Mock conversationService createQuestion
        when(conversationService.createQuestion(any(MessageDto.class))).thenReturn(question);

        //execute
        mockMvc.perform(post("/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message))
                .accept(MediaType.APPLICATION_JSON)
                )
                //collect and verify
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(TestData.Q_MESSAGE))
                .andExpect(jsonPath("$.author").value(TestData.Q_AUTHOR))
                .andExpect(jsonPath("$.replies").exists())
                .andExpect(jsonPath("$.replies").value(0))
                ;

        //verify that Svc method called
        verify(conversationService, times(1)).createQuestion(message);
    }

    /**
     * Tests successful execution scenario for POST operation to create a question at http://localhost:{PORT}/questions
     * The scenario is when conversationService throws DataAccessException originated in underlying repository
     * Expected response status is 500
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void createQuestion_500_when_svc_fails() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        question.getReplies().clear();
        final ObjectMapper mapper = new ObjectMapper();
        val message = new MessageDto(TestData.Q_AUTHOR, TestData.Q_MESSAGE);
        //Mock conversationService createQuestion
        when(conversationService.createQuestion(any(MessageDto.class))).thenThrow(new DataRetrievalFailureException("generated"));

        //execute
        mockMvc.perform(post("/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message))
                .accept(MediaType.APPLICATION_JSON)
        )
                //collect and verify
                .andExpect(status().isInternalServerError())
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).createQuestion(message);
    }

    /**
     * Tests successful execution scenario for POST operation to create a reply for a question at http://localhost:{PORT}/questions/{questionId}/reply
     * Expected response status is 201
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void createReplyForQuestion_201_when_all_good() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        final ObjectMapper mapper = new ObjectMapper();
        val message = new MessageDto(TestData.R_AUTHOR, TestData.R_MESSAGE);
        //Mock conversationService
        when(conversationService.findQuestion(anyLong())).thenReturn(question);
        when(conversationService.createReplyForQuestion(any(Question.class), any(MessageDto.class))).thenReturn(question.getReplies().iterator().next());

        //execute
        mockMvc.perform(post("/questions/1/reply/")
                //collect and verify
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(TestData.R_MESSAGE))
                .andExpect(jsonPath("$.author").value(TestData.R_AUTHOR))
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).findQuestion(1);
        verify(conversationService, times(1)).createReplyForQuestion(question, message);
    }

    /**
     * Tests failure scenario of Question is not found for POST operation to create a reply for a question at http://localhost:{PORT}/questions/{questionId}/reply
     * Expected response status is 400
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void createReplyForQuestion_400_when_no_record() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        final ObjectMapper mapper = new ObjectMapper();
        val message = new MessageDto(TestData.R_AUTHOR, TestData.R_MESSAGE);
        //Mock conversationService createQuestion
        when(conversationService.findQuestion(anyLong())).thenThrow(new RecordNotFoundException("generated"));
        when(conversationService.createReplyForQuestion(any(Question.class), any(MessageDto.class))).thenReturn(question.getReplies().iterator().next());

        //execute
        mockMvc.perform(post("/questions/1/reply/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message))
                .accept(MediaType.APPLICATION_JSON)
        )
                //collect and verify
                .andExpect(status().isBadRequest())
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).findQuestion(1);
        verify(conversationService, times(0)).createReplyForQuestion(question, message);
    }

    /**
     * Tests successful execution scenario for POST operation to create a reply for a question at http://localhost:{PORT}/questions/{questionId}/reply
     * The scenario is when the first call to conversationService.findQuestion() throws DataAccessException originated in underlying repository
     * Expected response status is 500
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void createReplyForQuestion_500_when_svc_fails_on_first_call() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        final ObjectMapper mapper = new ObjectMapper();
        val message = new MessageDto(TestData.R_AUTHOR, TestData.R_MESSAGE);
        //Mock conversationService call
        when(conversationService.findQuestion(anyLong())).thenThrow(new DataRetrievalFailureException("generated"));
        when(conversationService.createReplyForQuestion(any(Question.class), any(MessageDto.class))).thenReturn(question.getReplies().iterator().next());

        //execute
        mockMvc.perform(post("/questions/1/reply/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message))
                .accept(MediaType.APPLICATION_JSON)
        )
                //collect and verify
                .andExpect(status().isInternalServerError())
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).findQuestion(1);
        verify(conversationService, times(0)).createReplyForQuestion(question, message);
    }


    /**
     * Tests successful execution scenario for POST operation to create a reply for a question at http://localhost:{PORT}/questions/{questionId}/reply
     * The scenario is when the second call to conversationService.createReplyForQuestion() throws DataAccessException originated in underlying repository
     * Expected response status is 500
     * @throws Exception Possible exception of the test method
     */
    @Test
    public void createReplyForQuestion_500_when_svc_fails_on_second_call() throws Exception {
        //prepare
        val question = TestData.createTestQuestion();
        final ObjectMapper mapper = new ObjectMapper();
        val message = new MessageDto(TestData.R_AUTHOR, TestData.R_MESSAGE);
        //Mock conversationService call
        when(conversationService.findQuestion(anyLong())).thenReturn(question);
        when(conversationService.createReplyForQuestion(any(Question.class), any(MessageDto.class))).thenThrow(new DataRetrievalFailureException("generated"));

        //execute
        mockMvc.perform(post("/questions/1/reply/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message))
                .accept(MediaType.APPLICATION_JSON)
                )
                //collect and verify
                .andExpect(status().isInternalServerError())
        ;

        //verify that Svc method called
        verify(conversationService, times(1)).findQuestion(1);
        verify(conversationService, times(1)).createReplyForQuestion(question, message);
    }


}

