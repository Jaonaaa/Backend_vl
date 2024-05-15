package com.popo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popo.UserAuth.Request.AuthenticationRequest;
import com.popo.services.DBService;
import com.popo.utils.Status;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@RequestMapping("/db")
@RequiredArgsConstructor
public class DBController {

    private final DBService dbService;

    @DeleteMapping("/all")
    public Status deleteAll(@RequestBody AuthenticationRequest admin) {
        dbService.dropAll();
        return Status.ok("All data cleaned", null);
    }

    @ExceptionHandler(Exception.class)
    public Status handleException(Exception e) {
        e.printStackTrace();
        return Status.error(e.getMessage(), null);
    }

}
