package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @GetMapping(value = "/edit")
    public String editUser(Model model, @RequestParam int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "newUser";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        if (userService.getUserById(user.getId()) != null) {
            userService.updateUser(user);
        } else {
            userService.addUser(user);
        }
        return "redirect:/users";
    }

}