package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;
import java.util.Set;

public interface RoleDao {
    void saveRole(Role role);    // Create

    List<Role> getAllRoles();    // Read



}
