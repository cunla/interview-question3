package com.example.demo.repository;

import com.example.demo.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository to manage Reply entity persistence
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}

