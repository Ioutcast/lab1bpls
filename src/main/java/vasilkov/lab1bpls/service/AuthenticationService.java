package vasilkov.lab1bpls.service;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vasilkov.lab1bpls.entity.Role;
import vasilkov.lab1bpls.entity.Token;
import vasilkov.lab1bpls.entity.TokenType;
import vasilkov.lab1bpls.entity.User;
import vasilkov.lab1bpls.exception.MyPSQLException;
import vasilkov.lab1bpls.exception.ResourceNotFoundException;
import vasilkov.lab1bpls.model.AuthenticationRequest;
import vasilkov.lab1bpls.model.AuthenticationResponse;
import vasilkov.lab1bpls.model.RegisterRequest;
import vasilkov.lab1bpls.repository.TokenRepository;
import vasilkov.lab1bpls.repository.UserRepository;
import vasilkov.lab1bpls.security.JwtService;

import java.sql.SQLException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;


    public void register(RegisterRequest request) {
        try {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();
            repository.save(user);
        } catch (Exception ex) {
            if (ex.getCause() instanceof SQLException)
                throw new MyPSQLException("PSQLException");
            if (ex.getCause() instanceof PersistenceException)
                throw new MyPSQLException("MyConstraintViolationException");
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Error: user Not Found"));

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .username(user.getUsername())
                .type(TokenType.BEARER.toString())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}