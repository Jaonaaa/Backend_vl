package com.popo.controllers.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popo.UserAuth.Enum.Role;
import com.popo.UserAuth.Repository.UserRepository;
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
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final UserRepository userRepository;

    @GetMapping
    public Status getAll() {
        return Status.ok("", userRepository.findAllByRoles(Role.USER));
    }

    @ExceptionHandler(Exception.class)
    public Status handleException(Exception e) {
        e.printStackTrace();
        return Status.error(e.getMessage(), null);
    }

}
