package org.sayres.springmvc.controllers;

import org.sayres.springmvc.dao.UserDAO;
import org.sayres.springmvc.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


/**
 * @author Aliaksei Karabelnikau
 */

@Controller
public class LoginController {

    private final UserDAO userDAO;

    public LoginController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/reset")
    public String reset() {
        return "auth/reset";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "auth/register";
        userDAO.register(user);
        return "redirect:/login";
    }
}

