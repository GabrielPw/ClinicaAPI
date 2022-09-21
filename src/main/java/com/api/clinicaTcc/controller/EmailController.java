package com.api.clinicaTcc.controller;

import com.api.clinicaTcc.dtos.EmailDto;
import com.api.clinicaTcc.model.EmailModel;
import com.api.clinicaTcc.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {


    @Autowired
    EmailService emailService;

    @PostMapping("sending-email")
    public ResponseEntity<EmailModel> enviarEmail(@RequestBody @Valid EmailDto emailDto){

        EmailModel emailModel = new EmailModel();

        BeanUtils.copyProperties(emailDto, emailModel);  // <-- Converte o DTO para Model, para que fique apto a ser salvo no banco.

        emailService.enviarEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }
}
