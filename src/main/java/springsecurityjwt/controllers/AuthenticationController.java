package springsecurityjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import springsecurityjwt.model.Login;
import springsecurityjwt.model.LoginResponse;
import springsecurityjwt.service.CustomUserDetailsService;
import springsecurityjwt.service.TokenUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final TokenUtil tokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, TokenUtil tokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("/public/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Login login) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Credentials");
        }

        final String jwt = tokenUtil.generateToken(userDetailsService.loadUserByUsername(login.getUsername()));

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

}
