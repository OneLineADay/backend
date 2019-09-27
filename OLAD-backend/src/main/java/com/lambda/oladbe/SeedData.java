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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

        // WIP setting up simple date format
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String date = simpleDateFormat.format(new Date());
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);

        ArrayList<Entry> entries = new ArrayList<>();

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), role));
        User u1 = new User("Johnny", "Lambda", "Johnny@email.com", users, entries);

//        u1.getUserentries().add(new Entry("22-09-2019", "Today was a good day.", u1));
//        u1.getUserentries().add(new Entry("22-09-2018", "I ate a sandwich.", u1));
//        u1.getUserentries().add(new Entry("22-09-2017", "I talked to my mom today.", u1));
//        u1.getUserentries().add(new Entry("23-09-2017", "I took a walk in Central park.", u1));
//        u1.getUserentries().add(new Entry("24-09-2017", "contemplating the meaning of life.", u1));
//        u1.getUserentries().add(new Entry("25-09-2017", "I talked to my mom today.", u1));

        userService.save(u1);
//        Entry e1 = new Entry(String (), "Today was a good day.", u1);
//        entryService.save(e1);
        System.out.println("USER1____" + u1);

        ArrayList<UserRoles> users2 = new ArrayList<>();
        users2.add(new UserRoles(new User(), role));
        User u2 = new User("cinnamon", "1234567","cinnamon@mymail.local", users2, entries);
        u2.getUserentries().add(new Entry(strDate , "I wonder what the meaning of life is today.", u2));
        userService.save(u2);

        ArrayList<UserRoles> users3 = new ArrayList<>();
        users3.add(new UserRoles(new User(), role));
        User u3 = new User("barnbarn", "ILuvM4th!", "barnbarn@email.local", users, entries);
        u3.getUserentries().add(new Entry(strDate , "Today was not a good day.", u3));
        userService.save(u3);

        ArrayList<UserRoles> users4 = new ArrayList<>();
        users4.add(new UserRoles(new User(), role));
        User u4 = new User("Bob", "password", "bob@bob.com", users, entries);
        u4.getUserentries().add(new Entry(strDate , "I had lunch with a friend today.", u4));
        userService.save(u4);

        ArrayList<UserRoles> users5 = new ArrayList<>();
        users5.add(new UserRoles(new User(), role));
        User u5 = new User("Jane", "password", "jane@janerocks.com",users, entries);
        u5.getUserentries().add(new Entry(strDate , "I ate a veggie burger for lunch.", u5));
        userService.save(u5);
    }
}