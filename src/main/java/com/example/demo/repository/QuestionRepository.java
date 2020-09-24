package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository to manage Question entity persistence
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
