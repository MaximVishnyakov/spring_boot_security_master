package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public DBInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsersWithRoles() {

        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");

        roleService.save(role1);
        roleService.save(role2);

        User user1 = new User("Vasya", "Root", "VasyaRoot");
        User user2 = new User("Maxim", "King","MaximKing");
        User user3 = new User("Test", "Test","TestTest");


        user1.setRoles(new HashSet<>(Set.of(role1,role2)));
        user2.setRoles(new HashSet<>(Set.of(role1)));
        user3.setRoles(new HashSet<>(Set.of(role2)));

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

    }

}