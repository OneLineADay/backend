package com.lambda.oladbe.services;

import com.lambda.oladbe.models.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService
{
    UserDetails loadUserByUsername(String username);

    List<User> findAll();

    User findUserById(long id);

    User findByName(String name);

    void delete(long id);

    User save(User user);

    User update(User user, long id);

    void deleteUserRole(long userid, long roleid);

    void addUserRole(long userid, long roleid);
}