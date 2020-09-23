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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ConversationControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllQuestions() throws Exception {
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

    @Test
    public void getOneQuestion() throws Exception {
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

    @Test
    public void createQuestion() throws Exception {
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

    @Test
    public void createReplyForQuestion() throws Exception {
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
