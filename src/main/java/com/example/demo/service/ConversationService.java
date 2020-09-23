package com.example.demo.service;

import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;

import java.util.List;

public interface ConversationService {

    List<Question> listQuestions();

    Question findQuestion(long id);

    Question createQuestion(MessageDto message);

    Reply createReplyForQuestion(Question question, MessageDto message);
}


