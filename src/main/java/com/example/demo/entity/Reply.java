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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String message;


    @JsonIgnore
    @JoinColumn(name="question_id", referencedColumnName="id")
    @ManyToOne
    Question question;

    public Reply(String ath, String msg, Question qstn) {
        this.author = ath;
        this.message = msg;
        this.question = qstn;
    }
    public ReplyShortDto ToShortDto() {
        return new ReplyShortDto(id, getAuthor(), getMessage(), question.getId());
    }
}
