package com.example.demo.service;

import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ConversationServiceImpl implements ConversationService {

    private QuestionRepository questionRepository;
    private ReplyRepository replyRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) { this.questionRepository = questionRepository; }

    @Autowired
    public void setReplyRepository(ReplyRepository replyRepository) { this.replyRepository = replyRepository; }



    @Override
    public List<Question> listQuestions() {
        return questionRepository.findAll();
    }



    @Override
    public Question findQuestion(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if(optionalQuestion.isPresent())
            return optionalQuestion.get();
        else
            throw new RecordNotFoundException("Questions Not Found");
    }

    @Override
    public Question createQuestion(MessageDto message){
        return questionRepository.saveAndFlush(new Question(message.getAuthor(), message.getMessage()));
    }

    @Override
    public Reply createReplyForQuestion(Question question, MessageDto message){
        return replyRepository.saveAndFlush(new Reply(message.getAuthor(), message.getMessage(), question));
    }

}
