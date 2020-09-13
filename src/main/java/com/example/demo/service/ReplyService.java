package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Question;
import com.example.demo.model.Reply;
import com.example.demo.repository.ReplyRepository;

@Service
@Transactional
public class ReplyService {
     
    @Autowired
    ReplyRepository repository;
     
    @Autowired
    QuestionService questionService;
    
    public Reply createReply(Reply entity, Long questionId) throws ResourceNotFoundException 
    {
    	//Checking Question is exist
    	Optional<Question> question = Optional.of(questionService.getQuestionById(questionId));
         
        if(question.isPresent()) 
        {
            
        	// Create Reply
        	//entity.setQuestionId(question.get().getId());
        	entity.setQuestion(question.get());
            entity = repository.save(entity);
             
            return entity;
        } else {
        	throw new ResourceNotFoundException("No question record exist for given id");
        }
    } 
     
}
