package br.com.roupaspet.controllers;

import br.com.roupaspet.dtos.request.auth.SignInRequestDTO;
import br.com.roupaspet.dtos.request.auth.SignUpRequestDTO;
import br.com.roupaspet.dtos.response.auth.UserResponseDTO;
import br.com.roupaspet.infra.TokenService;
import br.com.roupaspet.models.User;
import br.com.roupaspet.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(
            AuthenticationManager manager,
            TokenService tokenService,
            AuthenticationService authenticationService //
    ) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDTO> signIn(@RequestBody SignInRequestDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = manager.authenticate(authenticationToken);

        var user = (User) authentication.getPrincipal();
        var tokenJWT = tokenService.generateToken(user);

        return ResponseEntity.ok(new UserResponseDTO(user.getName(), user.getEmail(), tokenJWT));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequestDTO dto) {
        authenticationService.create(dto);
        return ResponseEntity.created(null).build();
    }
}
