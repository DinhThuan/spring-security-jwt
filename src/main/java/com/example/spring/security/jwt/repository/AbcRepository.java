package com.example.spring.security.jwt.repository;

import com.example.spring.security.jwt.entities.Abc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbcRepository extends CrudRepository<Abc, Long> {
}
