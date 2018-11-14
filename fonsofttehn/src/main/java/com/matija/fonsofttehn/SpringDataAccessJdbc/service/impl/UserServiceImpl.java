package com.matija.fonsofttehn.SpringDataAccessJdbc.service.impl;

import com.matija.fonsofttehn.SpringDataAccessJdbc.model.User;
import com.matija.fonsofttehn.SpringDataAccessJdbc.repo.UserRepository;
import com.matija.fonsofttehn.SpringDataAccessJdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Override
    public List<User> getAll() {
        return userRepo.getAll();
    }
}
