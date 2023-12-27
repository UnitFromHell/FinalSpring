package com.example.itog.service;

import com.example.itog.models.Person;
import com.example.itog.repositories.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    private final PersonRepository userRepository;
    public UserService(PersonRepository userRepository){
        this.userRepository = userRepository;
    }

    public Person getUserByLogin(String login){
        Person user = userRepository.findByLogin(login);
        return user;
    }
    public Person createUser(Person user){
        Person newUser = userRepository.save(user);
        userRepository.flush();
        return newUser;
    }
}