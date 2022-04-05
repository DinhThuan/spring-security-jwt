package com.example.spring.crud.phrase.repository;

import com.example.spring.crud.phrase.entities.DeveloperTutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperTutorialRepository extends JpaRepository<DeveloperTutorial, Integer> {
}
