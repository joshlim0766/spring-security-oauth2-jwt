package josh0766.oauth2jwtexample.controller;

import josh0766.oauth2jwtexample.controller.dto.LoginResponse;
import josh0766.oauth2jwtexample.controller.dto.ResetTokenResponse;
import josh0766.oauth2jwtexample.controller.dto.SingupResponse;
import josh0766.oauth2jwtexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(
            value = "/test",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public String test () {
        return "{ \"result\" : \"test\" }";
    }

    @PostMapping(
            value = "/signup",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public SingupResponse singup (@RequestBody MultiValueMap<String, String> signupInformation) {
        return userService.signup(signupInformation);
    }

    @PostMapping(
            value = "/login",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public LoginResponse login (@RequestBody MultiValueMap<String, String> loginInformation) {
        return userService.login(loginInformation);
    }

    @PostMapping(
            value = "/reset",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public ResetTokenResponse resetToken (HttpServletRequest request) {
        return null;
    }

}
