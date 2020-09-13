package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Question;
import com.example.demo.model.Reply;
import com.example.demo.service.QuestionService;
import com.example.demo.service.ReplyService;


@RestController
@RequestMapping("/questions")
public class QuestionsController {
    @Autowired
    QuestionService questionService;
    
    @Autowired
    ReplyService replyService;

    /**
     * Return all questions from data base
     * @return - List<Question>
     */
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> list = questionService.getAllQuestions();

        return new ResponseEntity<List<Question>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    
    /**
     * Return question based on QuestionId
     * @param id - QuestionId
     * @return - Question
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Question entity = questionService.getQuestionById(id);

        return new ResponseEntity<Question>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Create new Question in database
     * @param Object of Question
     * @return - Created object from database
     * @throws ResourceNotFoundException
     */
    @PostMapping
    public ResponseEntity<Question> createOrUpdateQuestion(@RequestBody Question question)
            throws ResourceNotFoundException {
        Question updated = questionService.createOrUpdateQuestion(question);
        return new ResponseEntity<Question>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
    /**
     * Add Reply on Question
     * @param id - QuestionId
     * @param reply - Reply object
     * @return - Return Reply object
     * @throws ResourceNotFoundException
     */
    @PostMapping("/{id}/reply")
    public ResponseEntity<Reply> createOrUpdateReply(@PathVariable("id") Long id, @RequestBody Reply reply)
            throws ResourceNotFoundException {
        Reply entity = replyService.createReply(reply, id);

        return new ResponseEntity<Reply>(entity, new HttpHeaders(), HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public HttpStatus deleteQuestionById(@PathVariable("id") Long id)
//            throws ResourceNotFoundException {
//        service.deleteQuestionById(id);
//        return HttpStatus.FORBIDDEN;
//    }

}
