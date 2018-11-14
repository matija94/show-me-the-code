package com.matija.fonsofttehn.SpringDataAccessJdbc.repo.impl;

import com.matija.fonsofttehn.SpringDataAccessJdbc.model.User;
import com.matija.fonsofttehn.SpringDataAccessJdbc.repo.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public List<User> getAll() {
        return new ArrayList<>();
    }
}
