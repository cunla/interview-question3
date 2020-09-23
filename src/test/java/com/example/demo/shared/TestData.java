package com.example.demo.shared;

import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;

public class TestData {
    public static final String Q_AUTHOR = "author";
    public static final String Q_MESSAGE = "message";
    public static final String R_AUTHOR = "authorM";
    public static final String R_MESSAGE = "messageM";


    public static Question createTestQuestion() {
        return createTestQuestion(Q_AUTHOR, Q_MESSAGE, R_AUTHOR, R_MESSAGE);
    }
    public static Question createTestQuestion(String qauthor, String qmessage, String rauthor, String rmessage) {
        val question = new Question(1l, qauthor, qmessage, null);
        Collection<Reply> replies = new ArrayList<>();
        replies.add(new Reply(1l, rauthor, rmessage, question));
        question.setReplies(replies);
        return question;
    }

}
