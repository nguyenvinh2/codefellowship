package com.codefellows.vinh.codefellowship.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private String body;

    public long getpostId() {
        return postId;
    }

    public void setpostId(long id) {
        this.postId = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp.toString();
    }

    private String timeStamp;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public UserApplication getUserApplication() {
        return userApplication;
    }

    public void setUserApplication(UserApplication userApplication) {
        this.userApplication = userApplication;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userApplication", nullable = false)
    public UserApplication userApplication;
}
