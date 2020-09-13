package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="TBL_QUESTIONS")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Question {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    @Column(name="author")
    private String author;
     
    @Column(name="message")
    private String message;
    
    //@OneToMany(fetch=FetchType.LAZY, mappedBy="question", cascade=CascadeType.ALL)
    @OneToMany
    @JoinColumn(name = "questionId")
    private List<Reply> reply;
    
//    public List<Reply> getReply(){
//        return Optional.ofNullable(this.reply).orElseGet(ArrayList::new);
//    }

}
