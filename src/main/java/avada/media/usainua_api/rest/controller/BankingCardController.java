package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.BankingCard;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.service.BankingCardService;
import avada.media.usainua_api.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "banking card")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankingCardController {

    private final UserService userService;
    private final BankingCardService bankingCardService;

    @ApiOperation(value = "Get User Banking Cards List", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllBankingCards", produces = "application/json")
    public ResponseEntity<List<BankingCard>> getAllBankingCards(@ApiIgnore Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        return ResponseEntity.ok().body(bankingCardService.getAllBankingCardsByUserId(user.getId()));
    }

    @ApiOperation(value = "Add User Banking Card", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @PostMapping(value = "addBankingCard")
    public ResponseEntity<Void> addBankingCard(@ApiIgnore Principal principal,
                                               @Valid @RequestBody BankingCard bankingCardRequest) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + principal.getName()));
        bankingCardRequest.setMain(bankingCardService.getAllBankingCardsByUserId(user.getId()).isEmpty());
        bankingCardRequest.setUser(user);
        bankingCardService.saveBankingCard(bankingCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Delete User Banking Card by Id", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @DeleteMapping(value = "deleteBankingCard")
    public ResponseEntity<Void> deleteBankingCard(@RequestParam Long id) {
        bankingCardService.deleteBankingCardById(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Set Default User Banking Card by Id", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @PostMapping(value = "setDefaultBankingCard")
    public ResponseEntity<Void> setDefaultBankingCard(@RequestParam Long id) {
        BankingCard defaultBankingCard = bankingCardService.getDefaultBankingCard();
        if (defaultBankingCard != null) {
            defaultBankingCard.setMain(false);
            bankingCardService.saveBankingCard(defaultBankingCard);
        }
        BankingCard newDefaultBankingCard = bankingCardService.getBankingCardById(id);
        newDefaultBankingCard.setMain(true);
        bankingCardService.saveBankingCard(newDefaultBankingCard);
        return ResponseEntity.ok().build();
    }

}
