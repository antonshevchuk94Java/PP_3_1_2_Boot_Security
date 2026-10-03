package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.exception.DuplicateLoginException;
import ru.kata.spring.boot_security.demo.exception.UserDeleteException;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void saveUser(User user, List<String> roles) {
        if (userDao.findByLogin(user.getLogin()) != null) {
            throw new DuplicateLoginException("Этот login занят");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleSet(roleService.findRoles(roles));
        userDao.saveUser(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void updateUser(User user, List<String> roles) {
        User oldUser = userDao.findById(user.getId());
        if (oldUser == null) {
            throw new UserDeleteException("Пользователь был удален!");
        }
        user.setLogin(oldUser.getLogin());
        if (user.getPassword().isBlank()){
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRoleSet(roleService.findRoles(roles));
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);

    }

    @Override
    public User findByLogin(String userLogin) {
        return userDao.findByLogin(userLogin);
    }
}