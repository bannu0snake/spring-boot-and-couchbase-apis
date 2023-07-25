package com.mahaboob.assignment.services;

import com.mahaboob.assignment.models.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(final Employee manager, final Employee employee) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(manager.getEmail());
        message.setSubject("Addition of new employee under you");
        message.setText("Hi "+manager.getName()+"\n " +
                "new Employee has been added under you, please find employee email below."+
                "\n"+employee.getEmail());

        javaMailSender.send(message);
    }
}