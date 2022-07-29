package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.model.Product;
import avada.media.usainua_api.model.dto.OrderDTO;
import avada.media.usainua_api.model.order.Order;
import avada.media.usainua_api.model.order.SubOrder;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.repository.SubOrderRepo;
import avada.media.usainua_api.service.OrderService;
import avada.media.usainua_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@Api(tags = "orders")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final SubOrderRepo subOrderRepo;

    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "5";
    private final String DEFAULT_SORT_BY = "id";
    private final String DEFAULT_SORT_DIRECTION = "DESC";

    @ApiOperation(value = "Get All User Orders", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getOrdersByPage", produces = "application/json")
    public ResponseEntity<PageResponse<Order>> getOrdersByPage(
            @ApiIgnore Principal principal,
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        return ResponseEntity.ok().body(orderService.getOrdersByPageByUserId(user.getId(), pageNumber, pageSize, sortBy, sortDirection));
    }

    @ApiOperation(value = "Add User Order", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @PostMapping(value = "addOrder")
    public ResponseEntity<Void> addOrder(@ApiIgnore Principal principal,
                                                   @Valid @RequestBody OrderDTO orderDTO) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        Order order = new ObjectMapper().convertValue(orderDTO, Order.class);
        order.setUser(user);
        order.setStatus(Order.Status.CALCULATING_ORDER);
        subOrderRepo.saveAll(order.getSubOrders());
        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Get User Order by Id", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getOrderById", produces = "application/json")
    public ResponseEntity<Order> getOrdersByPage(@ApiIgnore Principal principal,
                                                 @RequestParam Long orderId) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        return ResponseEntity.ok().body(orderService.getOrderByIdAndUserId(orderId, user.getId()));
    }

    @ApiOperation(value = "Delete User Order by ID", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @DeleteMapping(value = "deleteOrder")
    public ResponseEntity<Void> deleteOrder(@RequestParam Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get All Additional Services", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllAdditionalServices", produces = "application/json")
    public ResponseEntity<SubOrder.AdditionalServices[]> getAllAdditionalServices() {
        return ResponseEntity.ok().body(SubOrder.AdditionalServices.values());
    }

}
