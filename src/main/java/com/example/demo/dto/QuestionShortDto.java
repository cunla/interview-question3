package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO to encapsulate a Question with a number of replies
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionShortDto {

    /**
     * Question Id
     */
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
     * Number of replies to the question
     */
    private int replies;
}
