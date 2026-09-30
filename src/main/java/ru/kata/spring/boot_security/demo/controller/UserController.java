package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin") // меняем что бы доступ остался только у админа
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping// Обрабатывает GET-запрос /admin, SuccessUserHandler отправляет админа сюда
    public String getAllUsers(ModelMap model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        // Получаем список пользователей из Service и передаём его в Model, чтобы Thymeleaf мог использовать его на странице allUser.html.
        return "allUser";
    }

    @PostMapping(value = "/save")
    public String saveUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") byte age) {
        User newUser = new User(firstName, lastName, age);
        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @PostMapping(value = "/update")
    public String updateUser(@RequestParam("id") long id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") byte age) {
        User mergeUser = new User(id, firstName, lastName, age);
        userService.updateUser(mergeUser);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    public String removeUserById(@RequestParam("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


}

