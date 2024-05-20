package br.com.roupaspet.helpers;

import br.com.roupaspet.infra.TokenService;
import br.com.roupaspet.models.User;
import br.com.roupaspet.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class GetUserFromTokenHelper {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public GetUserFromTokenHelper(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    public User getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String userEmail = null;


        if (authHeader != null && authHeader.contains("Bearer ")) {
            var jwtToken = authHeader.replace("Bearer ", "");
            userEmail = tokenService.getSubject(jwtToken);
        }
        return (User) userRepository.findByEmail(userEmail);
    }
}
