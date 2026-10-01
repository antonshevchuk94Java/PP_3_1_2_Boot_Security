package ru.kata.spring.boot_security.demo.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserInitialization implements CommandLineRunner {
    private final UserService userService;
    private final RoleDao roleDao;


    public UserInitialization(UserServiceImpl userService, RoleDaoImpl roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @Override
    public void run(String... args) {
        if (!roleDao.getAllRoles().isEmpty()) {
            return; // роли уже есть, значит, это не первый запуск
        }

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleDao.saveRole(adminRole);
        roleDao.saveRole(userRole);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setRoleSet(new HashSet<>(Set.of(adminRole, userRole)));
        userService.saveUser(admin);

        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setRoleSet(new HashSet<>(Set.of(userRole)));
        userService.saveUser(user);
    }
}