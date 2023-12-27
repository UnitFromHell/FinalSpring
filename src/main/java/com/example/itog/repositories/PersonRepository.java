package com.example.itog.repositories;


import com.example.itog.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {

    Person findByLogin(String username);

    boolean existsByLogin(String login);

}
