package UserServiceTwo.user.api;

import UserServiceTwo.user.dto.UserCreateDto;
import UserServiceTwo.user.dto.UserSignInDto;
import UserServiceTwo.user.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final KeycloakService keycloakService;

    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDto userSignInDto) {
        return keycloakService.signIn(userSignInDto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto) {
        keycloakService.createUser(userCreateDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
