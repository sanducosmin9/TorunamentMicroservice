package tournament.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tournament.configuration.JwtService;
import tournament.controller.authentication.AuthenticationRequest;
import tournament.controller.authentication.AuthenticationResponse;
import tournament.controller.authentication.RegisterRequest;
import tournament.model.Token;
import tournament.model.TokenType;
import tournament.model.TournamentUser;
import tournament.model.UserRole;
import tournament.repository.TokenRepository;
import tournament.repository.TournamentUserRepository;
import tournament.service.helper.SecurityUser;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public final String COOKIE_NAME = "auth_by_cookie";

    private final TournamentUserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = TournamentUser.builder()
                .id(0L)
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .tournaments(new ArrayList<>())
                .role(UserRole.ADMIN)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(new SecurityUser(savedUser));
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(request.getUsername())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with username " + request.getUsername() + " was not found")
                );
        var jwtToken = jwtService.generateToken(new SecurityUser(user));
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(request.getUsername())
                .build();
    }

    private void saveUserToken(TournamentUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(TournamentUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            }
        );
        tokenRepository.saveAll(validUserTokens);
    }

    public Cookie createCookie(String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(86400);
        cookie.setPath("/");
        return cookie;
    }
}
