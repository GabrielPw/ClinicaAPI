package com.api.clinicaTcc.service;

import com.api.clinicaTcc.model.EmailModel;
import com.api.clinicaTcc.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    public void enviarEmail(EmailModel emailModel) {
    }
}
