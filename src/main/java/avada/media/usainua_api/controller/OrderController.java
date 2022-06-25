package avada.media.usainua_admin.controller;

import avada.media.usainua_admin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService service;

    @GetMapping({"/", ""})
    public ModelAndView orders() {
        return new ModelAndView("/orders/index", "orders", service.getAllOrders());
    }

    @GetMapping("view/{id}")
    public ModelAndView viewOrder(@PathVariable("id") Long id) {
        return new ModelAndView("/orders/view", "order", service.getOrderById(id));
    }

    @GetMapping("delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        service.deleteOrder(id);
        return "redirect:/orders";
    }

}