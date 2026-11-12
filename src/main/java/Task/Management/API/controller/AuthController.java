
package Task.Management.API.controller;

import Task.Management.API.entity.User;
import Task.Management.API.dto.LoginRequest;
import Task.Management.API.dto.LoginResponse;
import Task.Management.API.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // User registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String password = request.get("password");

        User user = authService.register(email, password);

        return ResponseEntity.ok(
                Map.of("message", "User registered successfully",
                        "email", user.getEmail())
        );
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
