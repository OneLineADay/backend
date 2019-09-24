package com.lambda.oladbe;

import com.lambda.oladbe.models.Entry;
import com.lambda.oladbe.models.Role;
import com.lambda.oladbe.models.User;
import com.lambda.oladbe.models.UserRoles;
import com.lambda.oladbe.services.RoleService;
import com.lambda.oladbe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role role = new Role("user");

        roleService.save(role);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<>();

        users.add(new UserRoles(new User(), role));
        User u1 = new User("Johnny", "Lambda", "Johnny@email.com", users, entries);
//        u1.getUserentries().add(new Entry(new Date(), "Today was a good day.", u1));
        userService.save(u1);

        users.add(new UserRoles(new User(), role));
        User u2 = new User("cinnamon", "1234567","cinnamon@mymail.local", users, entries);
//        u2.getUserentries().add(new Entry(new Date(), "I wonder what the meaning of life is today.", u2));
        userService.save(u2);

        users.add(new UserRoles(new User(), role));
        User u3 = new User("barnbarn", "ILuvM4th!", "barnbarn@email.local", users, entries);
//        u3.getUserentries().add(new Entry(new Date(), "Today was not a good day.", u3));
        userService.save(u3);

        users.add(new UserRoles(new User(), role));
        User u4 = new User("Bob", "password", "bob@bob.com", users, entries);
//        u4.getUserentries().add(new Entry(new Date(), "I had lunch with a friend today.", u4));
        userService.save(u4);

        users.add(new UserRoles(new User(), role));
        User u5 = new User("Jane", "password", "jane@janerocks.com",users, entries);
//        u5.getUserentries().add(new Entry(new Date(), "I ate a veggie burger for lunch.", u5));
        userService.save(u5);
    }
}