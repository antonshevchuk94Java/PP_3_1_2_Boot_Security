package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.exception.DuplicateLoginException;
import ru.kata.spring.boot_security.demo.exception.UserDeleteException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;


@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(ModelMap model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "allUser";
    }

    @PostMapping("/save")
    public String saveUser(User user,
                           @RequestParam(value = "roles", required = false) List<String> roles,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.saveUser(user, roles);
        } catch (DuplicateLoginException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(User user,
                             @RequestParam(value = "roles", required = false) List<String> roles,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(user, roles);
        } catch (UserDeleteException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String removeUserById(@RequestParam("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

}