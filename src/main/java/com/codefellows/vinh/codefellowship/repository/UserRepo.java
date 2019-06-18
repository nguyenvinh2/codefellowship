package com.codefellows.vinh.codefellowship.repository;

import com.codefellows.vinh.codefellowship.model.UserApplication;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserApplication, Integer> {
    UserApplication findByUsername(String username);
}