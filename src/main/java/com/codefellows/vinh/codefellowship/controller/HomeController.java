package com.codefellows.vinh.codefellowship.controller;

import com.codefellows.vinh.codefellowship.model.UserApplication;
import com.codefellows.vinh.codefellowship.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal user, Model m) {
        m.addAttribute("user", user);
        try {
            UserApplication userRetrieval = userRepo.findByUsername(user.getName());
            if (userRetrieval == null) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "index";
    }

}