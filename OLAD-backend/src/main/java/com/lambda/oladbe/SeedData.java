package com.lambda.oladbe;

import com.lambda.oladbe.models.Entry;
import com.lambda.oladbe.models.Role;
import com.lambda.oladbe.models.User;
import com.lambda.oladbe.models.UserRoles;
import com.lambda.oladbe.services.EntryService;
import com.lambda.oladbe.services.RoleService;
import com.lambda.oladbe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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

    @Autowired
    EntryService entryService;

    @Override
    public void run(String[] args) throws Exception
    {
        Role role = new Role("user");

        roleService.save(role);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());

        // user
        ArrayList<Entry> entries = new ArrayList<>();

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), role));
        User u1 = new User("Johnny", "Lambda", "Johnny@email.com", users, entries);
        u1.getUserentries().add(new Entry(new Date() , "Today was a good day.", u1));
        u1.getUserentries().add(new Entry(new Date() , "Turkey Sandwich.", u1));
        u1.getUserentries().add(new Entry(new Date() , "I talked to my mom today.", u1));
        userService.save(u1);
//        Entry e1 = new Entry(String (), "Today was a good day.", u1);
//        entryService.save(e1);
        System.out.println("USER1____" + u1);

        ArrayList<UserRoles> users2 = new ArrayList<>();
        users2.add(new UserRoles(new User(), role));
        User u2 = new User("cinnamon", "1234567","cinnamon@mymail.local", users2, entries);
//        users.add(new UserRoles(u2, role));
        u2.getUserentries().add(new Entry(new Date() , "I wonder what the meaning of life is today.", u2));
        userService.save(u2);

        ArrayList<UserRoles> users3 = new ArrayList<>();
        users3.add(new UserRoles(new User(), role));
        User u3 = new User("barnbarn", "ILuvM4th!", "barnbarn@email.local", users, entries);
        u3.getUserentries().add(new Entry(new Date() , "Today was not a good day.", u3));
        userService.save(u3);

        ArrayList<UserRoles> users4 = new ArrayList<>();
        users4.add(new UserRoles(new User(), role));
        User u4 = new User("Bob", "password", "bob@bob.com", users, entries);
        u4.getUserentries().add(new Entry(new Date() , "I had lunch with a friend today.", u4));
        userService.save(u4);

        ArrayList<UserRoles> users5 = new ArrayList<>();
        users5.add(new UserRoles(new User(), role));
        User u5 = new User("Jane", "password", "jane@janerocks.com",users, entries);
        u5.getUserentries().add(new Entry(new Date() , "I ate a veggie burger for lunch.", u5));
        userService.save(u5);
    }
}