package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to encapsulate a Reply to a Question with question Id associated
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyShortDto {

    /**
     * Reply Id
     */
    private Long id;

    /**
     * Author name field
     */
    private String author;

    /**
     * Reply content field
     */
    private String message;

    /**
     * Question Id associated
     */
    Long questionId;
}
