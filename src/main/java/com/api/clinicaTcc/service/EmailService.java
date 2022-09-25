package com.api.clinicaTcc.service;

import com.api.clinicaTcc.enums.StatusEmailEnum;
import com.api.clinicaTcc.model.EmailModel;
import com.api.clinicaTcc.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;
    public EmailModel enviarEmail(EmailModel emailModel) {

        emailModel.setDataDeEnvio(LocalDateTime.now());

        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getTitulo());
            message.setText(emailModel.getTexto());

            emailSender.send(message);

            emailModel.setStatus(StatusEmailEnum.ENVIADO);
        } catch (MailException e){
            emailModel.setStatus(StatusEmailEnum.ERRO);
            e.printStackTrace();
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
