package com.popo.UserAuth.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popo.UserAuth.Request.AuthenticationRequest;
import com.popo.UserAuth.Request.RefreshTokenRequest;
import com.popo.UserAuth.Request.RegisterRequest;
import com.popo.UserAuth.Service.AuthService;
import com.popo.UserAuth.Service.CheckStatusService;
import com.popo.UserAuth.Service.RefreshTokenService;
import com.popo.utils.Status;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final RefreshTokenService refreshTokenService;
    private final CheckStatusService checkStatusService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/check")
    public ResponseEntity<Object> checkEmailAvailable(
            @PathParam(value = "email") String email) {
        return ResponseEntity.ok(!service.chekcIfAlreadyExist(email));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        ///
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/checkTokenStatus")
    public ResponseEntity<Object> checkStatus(
            @RequestBody CheckStatusResponse request) {
        ///
        return ResponseEntity.ok(checkStatusService.checkAll(request));
    }

    @DeleteMapping("/refreshToken")
    public ResponseEntity<Object> removeRefreshToken(
            @RequestBody RefreshTokenRequest request) {
        refreshTokenService.removeRefreshToken(request.getRefresh_token());
        return ResponseEntity.ok(Status.ok("refresh token removed", null));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Object> refreshToken(
            @RequestBody RefreshTokenRequest request) {
        ///
        try {
            return ResponseEntity.ok(service.useRefreshToken(request));
        } catch (Exception e) {
            return ResponseEntity.ok(Status.error(e.getMessage(), null));
        }

    }

}
