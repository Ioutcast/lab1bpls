package vasilkov.lab1bpls.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vasilkov.lab1bpls.model.AuthenticationRequest;
import vasilkov.lab1bpls.model.AuthenticationResponse;
import vasilkov.lab1bpls.model.MessageResponse;
import vasilkov.lab1bpls.model.RegisterRequest;
import vasilkov.lab1bpls.service.AuthenticationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;


    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(service.authenticate(request));
    }


}
