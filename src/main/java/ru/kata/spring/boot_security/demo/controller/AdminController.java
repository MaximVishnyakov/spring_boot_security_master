package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")

public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());

        return "/admin";
    }



    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user,
                      @RequestParam(value = "nameRoles") String[] roles) {
        Set<Role> roles1 = new HashSet<>();
        for (String role : roles) {
            roles1.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles1);
        userService.save(user);
        return "redirect:/admin/";
    }



    @GetMapping("/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "editRole") String[] roles,
                         @PathVariable("id") int id) {
        Set<Role> roles1 = new HashSet<>();
        for (String role : roles) {
            roles1.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles1);
        userService.update(id, user);
        return "redirect:/admin";
    }



    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/show";
    }

    //    @PostMapping()
//    public String createUser(@ModelAttribute("user") @Valid User user,
//                             BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "admin/new";
//        } else {
//            userService.save(user);
//            return "redirect:/admin";
//        }
//    }

    //    @PatchMapping("/{id}")
//    public String updateUser(@ModelAttribute("user") @Valid User user,
//                             BindingResult bindingResult,
//                             @PathVariable("id") int id) {
//        if (bindingResult.hasErrors()) {
//            return "admin/edit";
//        } else {
//            userService.update(id, user);
//            return "redirect:/admin";
//        }
//    }

}

