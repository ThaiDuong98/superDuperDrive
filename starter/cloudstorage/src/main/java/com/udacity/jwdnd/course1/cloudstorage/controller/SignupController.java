package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String signupUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){
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

        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute("signupError", errorMessage);
        } else {
            redirectAttributes.addFlashAttribute("signupSuccess", "You successfully signed up!");
            return "redirect:/login";
        }

        return "redirect:/signup";
    }
}
