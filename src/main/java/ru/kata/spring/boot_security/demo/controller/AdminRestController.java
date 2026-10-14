package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin/users")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public void saveUser(@RequestBody User user){
        List<String> roles = user.getRoleSet().stream()
                .map(r ->r.getRole())
                .collect(Collectors.toList());
        userService.saveUser(user, roles);

    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user){
        List<String> roles = user.getRoleSet().stream()
                .map(r ->r.getRole())
                .collect(Collectors.toList());
         userService.updateUser(user, roles);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<Role> getAllRole() { return roleService.getAllRoles(); }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.removeUserById(id);
    }

    @GetMapping("/{id}")
    public User getUsedByID(@PathVariable long id){ return userService.findById(id); }


}
