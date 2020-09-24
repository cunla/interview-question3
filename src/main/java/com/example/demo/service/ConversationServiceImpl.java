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

/**
 * Conversation Service to encapsulate the logic to expose and create Questions and Replies
 */
@Service
public class ConversationServiceImpl implements ConversationService {

    /**
     * Question Repository injected
     */
    private QuestionRepository questionRepository;

    /**
     * Reply Repository injected
     */
    private ReplyRepository replyRepository;

    /**
     * Setter method for Question Repository used by dependency injection
     * @param questionRepository Question Repository injected
     */
    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) { this.questionRepository = questionRepository; }

    /**
     * Setter method for Reply Repository used by dependency injection
     * @param replyRepository Reply Repository injected
     */
    @Autowired
    public void setReplyRepository(ReplyRepository replyRepository) { this.replyRepository = replyRepository; }


    /**
     * Exposes List of all Questions
     * @return List of Questions
     */
    @Override
    public List<Question> listQuestions() {
        return questionRepository.findAll();
    }


     /**
     * Exposes a Question by Id
     * @param id Question Id
     * @return Question instance
     * @throws RecordNotFoundException Record not found exception
     */
    @Override
    public Question findQuestion(long id) throws RecordNotFoundException {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if(optionalQuestion.isPresent())
            return optionalQuestion.get();
        else
            throw new RecordNotFoundException("Questions Not Found");
    }

    /**
     * Creates a new Question instance
     * @param message Question content (Author and Message fields)
     * @return the new Question created
     */
    @Override
    public Question createQuestion(MessageDto message){
        return questionRepository.saveAndFlush(new Question(message.getAuthor(), message.getMessage()));
    }

    /**
     * Create a new Reply to a Question
     * @param question Existing Question the Reply instance to create for
     * @param message Reply content (Author and Message fields)
     * @return ther new Reply Instacne
     */
    @Override
    public Reply createReplyForQuestion(Question question, MessageDto message){
        return replyRepository.saveAndFlush(new Reply(message.getAuthor(), message.getMessage(), question));
    }

}
