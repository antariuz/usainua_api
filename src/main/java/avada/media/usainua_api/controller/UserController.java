package avada.media.usainua_admin.controller;

import avada.media.usainua_admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService service;

    @GetMapping({"/", ""})
    public ModelAndView users() {
        return new ModelAndView("/users/index", "users", service.getAllUsers());
    }

    @GetMapping("view/{id}")
    public ModelAndView viewUser(@PathVariable("id") Long id) {
        return new ModelAndView("/users/view", "user", service.getUserById(id));
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
        return "redirect:/users";
    }

}