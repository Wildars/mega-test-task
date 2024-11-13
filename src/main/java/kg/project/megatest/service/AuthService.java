package kg.project.megatest.service;


import kg.project.megatest.model.security.JWTResponse;
import kg.project.megatest.model.security.MessageResponse;
import kg.project.megatest.model.security.SignInRequest;
import kg.project.megatest.model.security.SignUpRequest;

public interface AuthService {
    JWTResponse signIn(SignInRequest signInRequest);
    MessageResponse signUp(SignUpRequest signUpRequest);

}
