package com.example.demo.service;

import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;

import java.util.List;

/**
 * Contract for ConversationService
 */
public interface ConversationService {

    /**
     * Exposes List of all Questions
     * @return List of Questions
     */
    List<Question> listQuestions();

    /**
     * Exposes a Question by Id
     * @param id Question Id
     * @return Question instance
     */
    Question findQuestion(long id);

    /**
     * Creates a new Question instance
     * @param message Question content (Author and Message fields)
     * @return the new Question created
     */
    Question createQuestion(MessageDto message);

    /**
     * Create a new Reply to a Question
     * @param question Existing Question the Reply instance to create for
     * @param message Reply content (Author and Message fields)
     * @return ther new Reply Instacne
     */
    Reply createReplyForQuestion(Question question, MessageDto message);
}


