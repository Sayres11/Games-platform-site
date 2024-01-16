package org.sayres.springmvc.controllers;

import org.sayres.springmvc.dao.UserDAO;
import org.sayres.springmvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Aliaksei Karabelnikau
 */
@Controller
@RequestMapping("/users")


public class UserController {

    private final UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @GetMapping()
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page) {
        int totalUsers = userDAO.getTotalUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / 5);

        model.addAttribute("users", userDAO.getUsers(page, 5));
        model.addAttribute("page", page);
        model.addAttribute("size", 5);
        model.addAttribute("totalPages", totalPages);
        return "userControl/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        User user = userDAO.show(id);
        model.addAttribute("user", user);
        return "userControl/edit";
    }

    @GetMapping("/users")
    public String listUsers(Model model,
                            @RequestParam(defaultValue = "0") int page) {
        int totalUsers = userDAO.getTotalUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / 5);

        model.addAttribute("users", userDAO.getUsers(page, 5));
        model.addAttribute("page", page);
        model.addAttribute("size", 5);
        model.addAttribute("totalPages", totalPages);
        return "userControl/list";
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user) {
        user.setPassword("random");
        return "userControl/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return "userControl/new";
        }
        userDAO.save(user);
        return "redirect:/users";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDAO.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userDAO.delete(id);
        return "redirect:/users";
    }


}
