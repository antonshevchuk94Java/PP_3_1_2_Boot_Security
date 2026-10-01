package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.exception.DuplicateLoginException;
import ru.kata.spring.boot_security.demo.exception.UserDeleteException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public UserController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping
    public String getAllUsers(ModelMap model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", roleDao.getAllRoles());
        return "allUser";
    }

    @PostMapping("/save")
    public String saveUser(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("age") byte age,
                           @RequestParam("login") String login,
                           @RequestParam("password") String password,
                           @RequestParam(value = "roles", required = false) List<String> roles,
                           RedirectAttributes redirectAttributes) {
        User newUser = new User(0, firstName, lastName, age, login, password, findRoles(roles));
        try {
            userService.saveUser(newUser);
        } catch (DuplicateLoginException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") long id,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("age") byte age,
                             @RequestParam("password") String password,
                             @RequestParam(value = "roles", required = false) List<String> roles,
                             RedirectAttributes redirectAttributes) {
        User mergeUser = new User(id, firstName, lastName, age, password, findRoles(roles));
        try {
            userService.updateUser(mergeUser);
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

    // из отмеченных на форме названий ролей собираем объекты Role из базы
    private Set<Role> findRoles(List<String> names) {
        Set<Role> result = new HashSet<>();
        if (names == null) {
            return result;
        }
        for (Role role : roleDao.getAllRoles()) {
            if (names.contains(role.getRole())) {
                result.add(role);
            }
        }
        return result;
    }
}