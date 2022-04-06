package com.example.spring.security.jwt.repository;

import com.example.spring.security.jwt.entities.DeveloperTutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperTutorialRepository extends JpaRepository<DeveloperTutorial, Integer> {
}
