package com.codefellows.vinh.codefellowship.repository;

import com.codefellows.vinh.codefellowship.model.UserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserApplication, Long> {
    UserApplication findByUsername(String username);
}