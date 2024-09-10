package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getSignupPage(){
        return "signup";
    }

    @PostMapping()
    public RedirectView signupUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        String errorMessage = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            errorMessage = "The username already exists.";
        }

        if (errorMessage == null) {
            int newUser = userService.createUser(user);
            if (newUser < 0) {
                errorMessage = "There was an error signing you up. Please try again.";
            }
        }

        if (errorMessage == null) {
            RedirectView redirectView = new RedirectView("/login", true);
            redirectAttributes.addFlashAttribute("signupSuccess", "You successfully signed up!");
            return redirectView;
        }

        RedirectView redirectView = new RedirectView("/signup", true);
        redirectAttributes.addFlashAttribute("signupError", errorMessage);

        return redirectView;
    }
}
