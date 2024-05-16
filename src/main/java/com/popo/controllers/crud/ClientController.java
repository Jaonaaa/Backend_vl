package com.popo.controllers.crud;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popo.UserAuth.Enum.Role;
import com.popo.UserAuth.Models.User;
import com.popo.UserAuth.Repository.UserRepository;
import com.popo.UserAuth.Request.AuthenticationRequest;
import com.popo.UserAuth.Request.RefreshTokenRequest;
import com.popo.UserAuth.Request.RegisterRequest;
import com.popo.UserAuth.Service.AuthService;
import com.popo.UserAuth.Service.CheckStatusService;
import com.popo.UserAuth.Service.RefreshTokenService;
import com.popo.models.Devis;
import com.popo.repository.DevisRepository;
import com.popo.utils.Status;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final UserRepository userRepository;
    private final DevisRepository devisRepository;

    @GetMapping
    public Status getAll() {
        List<User> users = userRepository.findAllByRoles(Role.USER);
        for (User user : users) {
            List<Devis> devis = devisRepository.getByIdUser(user.getId().intValue());
            user.setDevis_count(devis.size());
        }
        return Status.ok("", users);
    }

    @ExceptionHandler(Exception.class)
    public Status handleException(Exception e) {
        e.printStackTrace();
        return Status.error(e.getMessage(), null);
    }

}
