package ru.kata.spring.boot_security.demo.dao;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);    // Create

    List<User> getAllUsers();    // Read

    void updateUser(User user);  // Update

    void removeUserById(long id); //Delete

    User findByLogin(String userLogin); // Find

    User findById(long id);


}
