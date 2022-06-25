package avada.media.usainua_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/", ""})
    public String redirectToDashboard() {
        return "redirect:/dashboard";
    }

}
