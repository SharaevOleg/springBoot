package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller

public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/users"})
    public String getUser(ModelMap model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", userDetails);
        return "users";
    }

    @GetMapping(value = "/admin")
    public String allUsers(ModelMap model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        model.addAttribute("allRoles", userService.getRole());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping(value = "/createUser")
    public String createUser(@ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/updateUser/{id}")
    public String updateUser(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", userService.getRole());
        return "updateUser";
    }

    @PostMapping(value = "/updateUser")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.remove(id);
        return "redirect:/admin";
    }



}
