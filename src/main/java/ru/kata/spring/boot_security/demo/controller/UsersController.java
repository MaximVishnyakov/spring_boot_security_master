package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;


@Controller
@RequestMapping("/")
public class UsersController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserService userService;



    @Autowired
    public UsersController(UserService userService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userService = userService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }


    @GetMapping()
    public String index(Model model, Principal principal) {

        if (principal != null) {

            User user = (User) userService.loadUserByUsername(principal.getName());
            Set<String> roles = AuthorityUtils.authorityListToSet(user.getAuthorities());
            boolean roleAdmin = roles.contains("ROLE_ADMIN");
            model.addAttribute("roleAdmin", roleAdmin);
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("users", userService.index());

        return "users/index";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        if (principal != null) {
            User user = (User) userService.loadUserByUsername(principal.getName());
            Set<String> roles = AuthorityUtils.authorityListToSet(user.getAuthorities());
            boolean roleAdmin = roles.contains("ROLE_ADMIN");
            model.addAttribute("username", principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            model.addAttribute("roleAdmin", roleAdmin);
        }

        return "users/user";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        if (principal != null) {
            User user = (User) userService.loadUserByUsername(principal.getName());
            Set<String> roles = AuthorityUtils.authorityListToSet(user.getAuthorities());
            model.addAttribute("username", principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
        }
        return "users/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") long id, Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("user", userService.show(id));
        return "users/show";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("user", userService.show(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/";
    }
}
