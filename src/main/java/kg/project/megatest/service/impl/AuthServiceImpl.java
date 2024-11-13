package kg.project.megatest.service.impl;

import kg.project.megatest.config.jwt.JWTUtils;
import kg.project.megatest.config.jwt.UserDetailsImpl;
import kg.project.megatest.entity.Role;
import kg.project.megatest.entity.User;
import kg.project.megatest.exception.AlreadyExistsException;
import kg.project.megatest.model.security.JWTResponse;
import kg.project.megatest.model.security.MessageResponse;
import kg.project.megatest.model.security.SignInRequest;
import kg.project.megatest.model.security.SignUpRequest;
import kg.project.megatest.repository.RoleRepository;
import kg.project.megatest.repository.UserRepository;
import kg.project.megatest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;



    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtils jwtUtils;

    @Override
    @Transactional
    public JWTResponse signIn(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found"));


        return new JWTResponse(jwt, userDetails.getId(), userDetails.getUsername(), role);
    }

    @Override
    @Transactional
    public MessageResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new AlreadyExistsException("Error: Username already exists");
        }

        User user = new User(signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName());

        Role role = roleRepository.findById(signUpRequest.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Error: Role not found"));

        user.setRole(role);
        userRepository.save(user);

        return new MessageResponse("User CREATED");
    }
}
