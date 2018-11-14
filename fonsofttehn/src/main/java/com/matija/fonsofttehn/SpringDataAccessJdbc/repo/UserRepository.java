package com.matija.fonsofttehn.SpringDataAccessJdbc.repo;

import com.matija.fonsofttehn.SpringDataAccessJdbc.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();
}
