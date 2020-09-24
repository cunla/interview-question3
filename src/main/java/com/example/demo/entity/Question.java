package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.demo.dto.QuestionShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Question Entity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question  {

    /**
     * Question Id
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
     * Replies collection
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Collection<Reply> replies = new ArrayList<>();

    /**
     * CTOR to construct an instance from Author and Message
     * @param ath Author
     * @param msg Message
     */
    public Question(String ath, String msg) {
        this.author = ath;
        this.message = msg;
    }

    /**
     * Derives Question Short DTO
     * @return Question Short DTO
     */
    public QuestionShortDto ToShortDto() {
        return new QuestionShortDto(id, getAuthor(), getMessage(),  getReplies().size());
    }
}
