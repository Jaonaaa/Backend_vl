package com.popo.controllers.crud;

import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popo.models.Lieu;
import com.popo.repository.LieuRepository;
import com.popo.utils.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lieu")
@RequiredArgsConstructor
public class LieuController {

    private final LieuRepository lieuRepository;

    @GetMapping
    public Status getAll() {
        List<Lieu> lieux = lieuRepository.findAll();
        return Status.ok("", lieux);
    }

    @ExceptionHandler(Exception.class)
    public Status handleException(Exception e) {
        e.printStackTrace();
        return Status.error(e.getMessage(), null);
    }

}
