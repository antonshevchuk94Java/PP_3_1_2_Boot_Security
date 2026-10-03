package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user, List<String> roles);    // Create

    List<User> getAllUsers();    // Read

    void updateUser(User user, List<String> roles);  // Update

    void removeUserById(long id); //Delete

    User findByLogin(String userLogin); // Find


}
