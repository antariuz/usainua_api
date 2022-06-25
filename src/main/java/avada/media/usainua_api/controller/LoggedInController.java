package avada.media.usainua_admin.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class LoggedInController {

    @ModelAttribute
    public void globalUserObject(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("loggedinuser", principal.getName());
        } else model.addAttribute("loggedinuser", "anonymous");
    }

}
