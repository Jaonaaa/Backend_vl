package com.popo.UserAuth.Service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.popo.UserAuth.Auth.AuthenticationResponse;
import com.popo.UserAuth.Auth.RefreshToken;
import com.popo.UserAuth.Config.JwtService;
import com.popo.UserAuth.Enum.Role;
import com.popo.UserAuth.Models.User;
import com.popo.UserAuth.Repository.UserRepository;
import com.popo.UserAuth.Request.AuthenticationRequest;
import com.popo.UserAuth.Request.RefreshTokenRequest;
import com.popo.UserAuth.Request.RegisterRequest;
import com.popo.utils.Status;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final RefreshTokenService refreshTokenService;

        public AuthenticationResponse register(RegisterRequest request) {
                User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
                                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                                .roles(Role.valueOf(request.getRole())).build();
                user = repository.save(user);
                return getAuthResponse(user);
        }

        public AuthenticationResponse registerClient(RegisterRequest request) {
                User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
                                .email(repository.count() + "@gmail.com").password(passwordEncoder.encode("popo"))
                                .roles((Role.USER)).numero(request.getNumero()).build();
                user = repository.save(user);
                return getAuthResponse(user);
        }

        public Role getRole(String roleS) {
                return Role.valueOf(roleS.toUpperCase());
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = repository.findByEmail(request.getEmail()).orElseThrow();
                return getAuthResponse(user);
        }

        public AuthenticationResponse authenticateClient(AuthenticationRequest request) {
                Optional<User> userOtp = repository.findByNumero(request.getNumero());
                User user = null;

                if (userOtp.isPresent())
                        user = userOtp.get();
                else {
                        user = repository.save(User.builder().firstname(request.getNumero()).lastname("")
                                        .email(repository.count() + "@gmail.com").password("popo")
                                        .roles((Role.USER)).numero(request.getNumero()).build());

                }
                if (user.getRoles().equals(Role.ADMIN))
                        throw new RuntimeException("Authentification as a client is not allowed");
                // authenticationManager.authenticate(
                // new UsernamePasswordAuthenticationToken(user.getEmail(),
                // user.getPassword()));
                return getAuthResponse(user);
        }

        public User saveUserClient(String numero) {
                User user = repository.save(User.builder().firstname(numero).lastname("")
                                .email(repository.count() + "@gmail.com").password("popo")
                                .roles((Role.USER)).numero(numero).build());
                return user;
        }

        public AuthenticationResponse getAuthResponse(User user) {
                String jwtToken = jwtService.generateToken(user);
                RefreshToken tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());
                return AuthenticationResponse.builder().token(jwtToken).user(user)
                                .refresh_token(tokenRefresh.getToken())
                                .build();
        }

        public Status useRefreshToken(RefreshTokenRequest refreshTokenRequest) {
                User user = refreshTokenService.findByToken(refreshTokenRequest.getRefresh_token()).map(
                                refreshTokenService::verifyExpiration).map(RefreshToken::getUser).get();
                String accesToken = jwtService.generateToken(user);
                var tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());

                return Status.builder().build().ok_("token removed",
                                AuthenticationResponse.builder().token(accesToken).user(user)
                                                .refresh_token(tokenRefresh.getToken())
                                                .build());
        }

        public Boolean chekcIfAlreadyExist(String email) {
                Boolean exist = false;
                Optional<User> user = repository.findByEmail(email);
                if (user.isPresent())
                        exist = true;

                return exist;
        }
}
