package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to encapsulate a Message with Author and Message fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    /**
     * Author name field
     */
    private String author;

    /**
     * Message content field
     */
    private String message;
}
