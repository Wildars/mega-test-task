package kg.project.megatest.controller;

import kg.project.megatest.model.security.JWTResponse;
import kg.project.megatest.model.security.MessageResponse;
import kg.project.megatest.model.security.SignInRequest;
import kg.project.megatest.model.security.SignUpRequest;
import kg.project.megatest.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        JWTResponse response = authService.signIn(signInRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        MessageResponse messageResponse = authService.signUp(signUpRequest);

        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("Logout successful"));
    }

}