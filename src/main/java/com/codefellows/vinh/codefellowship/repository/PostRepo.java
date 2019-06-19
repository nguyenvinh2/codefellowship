package com.codefellows.vinh.codefellowship.repository;

import com.codefellows.vinh.codefellowship.model.Post;
import com.codefellows.vinh.codefellowship.model.UserApplication;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepo extends CrudRepository<Post, Long> {
    public Iterable<Post> findByUserApplication(UserApplication user);
    public void deleteByPostId(long id);
}
