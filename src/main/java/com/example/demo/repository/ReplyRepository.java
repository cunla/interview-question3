package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Reply;

 
@Repository
public interface ReplyRepository 
        extends JpaRepository<Reply, Long> {
 
}
