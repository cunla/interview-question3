package com.example.demo.web;

import com.example.demo.dto.MessageDto;
import com.example.demo.dto.QuestionShortDto;
import com.example.demo.dto.ReplyShortDto;
import com.example.demo.entity.Question;
import com.example.demo.shared.TestData;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * ConversationController integration tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ConversationControllerIntegrationTest {

    /**
     * Port used Http operations
     */
    @LocalServerPort
    private int port;

    /**
     * Injected TestRestTemplate
     */
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests successful execution scenario for GET operation to get list questions at http://localhost:{PORT}/questions
     * Expected response status is 200
     */
    @Test
    public void getAllQuestions() {
        //prepare
        //no need to prepare data as data is created from data.sql

        //execute
        ResponseEntity<List> responseEntity =  this.restTemplate.getForEntity("http://localhost:" + port + "questions/", List.class);

        //collect data
        int status = responseEntity.getStatusCodeValue();
        val actual = responseEntity.getHeaders();
        List<QuestionShortDto> list = responseEntity.getBody();

        //verify correct status is returned
        assertEquals("Correct Response Status", HttpStatus.OK.value(), status);

        //verify data returned
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    /**
     * Tests successful execution scenario for GET operation to get a question at http://localhost:{PORT}/questions/{questionId}
     * Expected response status is 200
     */
    @Test
    public void getOneQuestion() {
        //prepare
        //no need to prepare data as data is created from data.sql

        //execute
        ResponseEntity<Question> responseEntity =  this.restTemplate.getForEntity("http://localhost:" + port + "questions/1/", Question.class);

        //collect data
        int status = responseEntity.getStatusCodeValue();
        Question instance = responseEntity.getBody();
        val actual = responseEntity.getHeaders();

        //verify correct status is returned
        assertEquals("Correct Response Status", HttpStatus.OK.value(), status);

        //verify data returned
        assertNotNull(instance);
        assertEquals("dima", instance.getAuthor() );
    }

    /**
     * Tests successful execution scenario for POST operation to create a question at http://localhost:{PORT}/questions
     * Expected response status is 201
     */
    @Test
    public void createQuestion() {
        //prepare
        val message = new MessageDto(TestData.Q_AUTHOR, TestData.Q_MESSAGE);

        //execute
        ResponseEntity<QuestionShortDto> responseEntity =  this.restTemplate.postForEntity("http://localhost:" + port + "questions/", message, QuestionShortDto.class);

        //collect data
        int status = responseEntity.getStatusCodeValue();
        QuestionShortDto instance = responseEntity.getBody();

        //verify an CREATED status is returned
        assertEquals("Correct Response Status", HttpStatus.CREATED.value(), status);

        //verify data returned
        assertNotNull(instance);
        assertEquals(instance.getAuthor(), message.getAuthor());
    }

    /**
     * Tests successful execution scenario for POST operation to create a reply for a question at http://localhost:{PORT}/questions/{questionId}/reply
     * Expected response status is 201
     */
    @Test
    public void createReplyForQuestion() {
        //prepare
        val message = new MessageDto(TestData.R_AUTHOR, TestData.R_MESSAGE);

        //execute
        ResponseEntity<ReplyShortDto> responseEntity =  this.restTemplate.postForEntity("http://localhost:" + port + "questions/1/reply", message, ReplyShortDto.class);

        //collect data
        int status = responseEntity.getStatusCodeValue();
        ReplyShortDto instance = responseEntity.getBody();

        //verify an CREATED status is returned
        assertEquals("Correct Response Status", HttpStatus.CREATED.value(), status);

        //verify data returned
        assertNotNull(instance);
        assertEquals(instance.getAuthor(), message.getAuthor());
    }


}
