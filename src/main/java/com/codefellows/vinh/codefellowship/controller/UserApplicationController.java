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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        System.out.println(followers.get(0).getUsername());
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
}
