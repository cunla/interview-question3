package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {
     
    @Autowired
    QuestionRepository repository;
     
    public List<Question> getAllQuestions()
    {
        List<Question> questionList = repository.findAll();
         
        if(questionList.size() > 0) {
            return questionList;
        } else {
            return new ArrayList<Question>();
        }
    }
     
    public Question getQuestionById(Long id) throws ResourceNotFoundException 
    {
        Optional<Question> question = repository.findById(id);
         
        if(question.isPresent()) {
            return question.get();
        } else {
            throw new ResourceNotFoundException("No question record exist for given id");
        }
    }
     
    public Question createOrUpdateQuestion(Question entity) throws ResourceNotFoundException 
    {
    	Optional<Question> question = Optional.empty();
    	if(entity.getId() != null) { 
    		question = repository.findById(entity.getId());
    	}
         
        if(question.isPresent()) 
        {
            Question newEntity = question.get();
            newEntity.setAuthor(entity.getAuthor());
 
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    } 
     
    public void deleteQuestionById(Long id) throws ResourceNotFoundException 
    {
        Optional<Question> question = repository.findById(id);
         
        if(question.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No question record exist for given id");
        }
    } 
}
