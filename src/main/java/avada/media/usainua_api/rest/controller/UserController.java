package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@Api(tags = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Get User", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getUser", produces = "application/json")
    public ResponseEntity<User> getUser(@ApiIgnore Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new EntityNotFoundException("Not found User with email: " + principal.getName()));
        return ResponseEntity.ok().body(user);
    }

}
