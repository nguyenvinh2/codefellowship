package com.codefellows.vinh.codefellowship.controller;

import com.codefellows.vinh.codefellowship.model.Post;
import com.codefellows.vinh.codefellowship.model.UserApplication;
import com.codefellows.vinh.codefellowship.repository.PostRepo;
import com.codefellows.vinh.codefellowship.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserApplicationController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RedirectView createUser(@ModelAttribute UserApplication user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @RequestMapping(value = "/myprofile", method = RequestMethod.GET)
    public String user(Principal user, Model m) {
        UserApplication getUser = userRepo.findByUsername(user.getName());
        Iterable<Post> getPost = postRepo.findByUserApplication(getUser);
        List<UserApplication> followers = new ArrayList<>(getUser.getFollowers());
        List<UserApplication> leaders = new ArrayList<>(getUser.getLeaders());
        m.addAttribute("followers", followers);
        m.addAttribute("leaders", leaders);
        m.addAttribute("userInfo", getUser);
        m.addAttribute("user", user);
        m.addAttribute("posts", getPost);
        return "user";
    }

    @RequestMapping(value = "/myprofile", method = RequestMethod.POST)
    public RedirectView user(Principal user, @ModelAttribute Post post) {
        UserApplication getUser = userRepo.findByUsername(user.getName());
        post.setTimeStamp(LocalDateTime.now());
        post.setUserApplication(getUser);
        postRepo.save(post);
        return new RedirectView("/myprofile");
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String feed(Principal user, Model m) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        UserApplication getUser = userRepo.findByUsername(user.getName());
        List<UserApplication> leaders = new ArrayList<>(getUser.getLeaders());
        List<Post> posts = new ArrayList<Post>();
        for(UserApplication account:leaders) {
            posts.addAll(account.getPosts());
        }
        posts.sort(Comparator.comparing(item -> LocalDateTime.parse(item.getTimeStamp(), formatter)));
        Collections.reverse(posts);
        m.addAttribute("user", user);
        m.addAttribute("posts", posts);
        return "feed";
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public String getUser(@PathVariable String userId, Principal user, Model m) {
        UserApplication getUser = userRepo.findByUsername(userId);
        m.addAttribute("userInfo", getUser);
        m.addAttribute("user", user);
        return "others";
    }

    @RequestMapping(value = "/userindex", method = RequestMethod.GET)
    public String userIndex(Principal user, Model m) {
        Iterable<UserApplication> users = userRepo.findAll();
        m.addAttribute("users", users);
        m.addAttribute("user", user);
        return "userindex";
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public String follow(Principal user, Model m, @RequestParam String followUser) {
        if(user.getName() != followUser) {
            UserApplication self = userRepo.findByUsername(user.getName());
            self.getLeaders().add(userRepo.findByUsername(followUser));
            userRepo.save(self);
        }
        return "redirect:/myprofile";
    }


}
