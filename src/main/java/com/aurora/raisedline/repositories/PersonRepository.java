package com.aurora.raisedline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurora.raisedline.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
