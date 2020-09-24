package com.example.demo.shared;

import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Encapsulates tests data generation used in tests
 */
public class TestData {

    /**
     * Author for a Question
     */
    public static final String Q_AUTHOR = "author";

    /**
     * Message for a Question
     */
    public static final String Q_MESSAGE = "message";

    /**
     * Author for a Reply
     */
    public static final String R_AUTHOR = "authorM";


    /**
     * Author for a Reply
     */
    public static final String R_MESSAGE = "messageM";


    /**
     * Instantiates a new Question using default constants for Author and Message fields
     * @return Question instance used in tests. The instance has one Reply
     */
    public static Question createTestQuestion() {
        return createTestQuestion(Q_AUTHOR, Q_MESSAGE, R_AUTHOR, R_MESSAGE);
    }

     /**
     * Instantiates a new Question using default input arguments for Author and Message fields
     * @param qauthor   Question's Author
     * @param qmessage Question's Message
     * @param rauthor Reply's Author
     * @param rmessage Reply's Message
     * @return Question instance used in tests. The instance has one Reply
     */
    public static Question createTestQuestion(String qauthor, String qmessage, String rauthor, String rmessage) {
        val question = new Question(1l, qauthor, qmessage, null);
        Collection<Reply> replies = new ArrayList<>();
        replies.add(new Reply(1l, rauthor, rmessage, question));
        question.setReplies(replies);
        return question;
    }

}
