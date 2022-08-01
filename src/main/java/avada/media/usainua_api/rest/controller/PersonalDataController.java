package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.user.PersonalData;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.service.PersonalDataService;
import avada.media.usainua_api.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/")
@Api(tags = "personal data")
@RequiredArgsConstructor
public class PersonalDataController {

    private final UserService userService;
    private final PersonalDataService personalDataService;

    @ApiOperation(value = "Get User Personal Data", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getPersonalData", produces = "application/json")
    public ResponseEntity<PersonalData> getPersonalData(@ApiIgnore Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new EntityNotFoundException("Not found User with email: " + principal.getName()));
        return ResponseEntity.ok().body(personalDataService.getPersonalDataById(user.getId()));
    }

    @ApiOperation(value = "Update User Personal Data", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @PutMapping(value = "updatePersonalData")
    public ResponseEntity<Void> updatePersonalData(@ApiIgnore Principal principal,
                                                   @Valid @RequestBody PersonalData personalDataRequest) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new EntityNotFoundException("Not found User with email: " + principal.getName()));
        PersonalData personalData = personalDataService.getPersonalDataById(user.getId());
        personalDataRequest.setId(personalData.getId());
        personalDataRequest.setUser(personalData.getUser());
        personalDataService.savePersonalData(personalDataRequest);
        return ResponseEntity.ok().build();
    }

}
