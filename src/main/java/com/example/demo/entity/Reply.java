package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.demo.dto.ReplyShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Reply Entity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reply {

    /**
     * Reply Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Author name field
     */
    private String author;

    /**
     * Question content field
     */
    private String message;

    /**
     * Question the Reply instance associated with
     */
    @JsonIgnore
    @JoinColumn(name="question_id", referencedColumnName="id")
    @ManyToOne
    Question question;

    /**
     * CTOR to construct an instance from Author, Message and associated Question
     * @param ath Author
     * @param msg Message
     * @param qstn Question instance
     */
    public Reply(String ath, String msg, Question qstn) {
        this.author = ath;
        this.message = msg;
        this.question = qstn;
    }

    /**
     * Derives Reply Short DTO
     * @return Reply Short DTO
     */
    public ReplyShortDto ToShortDto() {
        return new ReplyShortDto(id, getAuthor(), getMessage(), question.getId());
    }
}
