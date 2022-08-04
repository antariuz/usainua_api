package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.delivery.ShippingAddress;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.service.ShippingAddressService;
import avada.media.usainua_api.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/")
@Api(tags = "shipping address")
@RequiredArgsConstructor
public class ShippingAddressController {

    private final UserService userService;
    private final ShippingAddressService shippingAddressService;

    @ApiOperation(value = "Get All User Shipping Addresses", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllShippingAddresses", produces = "application/json")
    public ResponseEntity<List<ShippingAddress>> getAllShippingAddresses(@ApiIgnore Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        return ResponseEntity.ok().body(shippingAddressService.getAllShippingAddressByUserId(user.getId()));
    }

    @ApiOperation(value = "Add User Shipping Address", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @PostMapping(value = "addShippingAddress")
    public ResponseEntity<Void> addShippingAddress(@ApiIgnore Principal principal,
                                                   @Valid @RequestBody ShippingAddress shippingAddressRequest) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        shippingAddressRequest.setMain(shippingAddressService.getAllShippingAddressByUserId(user.getId()).isEmpty());
        shippingAddressRequest.setUser(user);
        shippingAddressService.saveShippingAddress(shippingAddressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update User Shipping Address", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @PutMapping(value = "updateShippingAddress")
    public ResponseEntity<Void> updateShippingAddress(@ApiIgnore Principal principal,
                                                      @Valid @RequestBody ShippingAddress shippingAddressRequest) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        shippingAddressRequest.setUser(user);
        shippingAddressService.saveShippingAddress(shippingAddressRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete User Shipping Address by ID", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @DeleteMapping(value = "deleteShippingAddress")
    public ResponseEntity<Void> deleteShippingAddress(@RequestParam Long id) {
        shippingAddressService.deleteShippingAddressById(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Set User Default Shipping Address by ID", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @PatchMapping(value = "setDefaultShippingAddress")
    public ResponseEntity<Void> setDefaultShippingAddress(@RequestParam Long id) {
        ShippingAddress defaultShippingAddress = shippingAddressService.getDefaultShippingAddress();
        if (defaultShippingAddress != null) {
            defaultShippingAddress.setMain(false);
            shippingAddressService.saveShippingAddress(defaultShippingAddress);
        }
        ShippingAddress newDefaultShippingAddress = shippingAddressService.getShippingAddressById(id);
        newDefaultShippingAddress.setMain(true);
        shippingAddressService.saveShippingAddress(newDefaultShippingAddress);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get All Countries", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllCountries", produces = "application/json")
    public ResponseEntity<ShippingAddress.Country[]> getAllCountries() {
        return ResponseEntity.ok().body(ShippingAddress.Country.values());
    }

}
