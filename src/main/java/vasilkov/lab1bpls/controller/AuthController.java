package vasilkov.lab1bpls.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vasilkov.lab1bpls.model.AuthenticationRequest;
import vasilkov.lab1bpls.model.AuthenticationResponse;
import vasilkov.lab1bpls.model.MessageResponse;
import vasilkov.lab1bpls.model.RegisterRequest;
import vasilkov.lab1bpls.service.AuthenticationService;
import vasilkov.lab1bpls.service.LogoutService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Bad request")
})
public class AuthController {

    private final AuthenticationService service;

    private final LogoutService logoutService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(new MessageResponse("Logout successfully"));
    }


}
