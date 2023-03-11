package com.perscholas.voyaging.service;

import com.perscholas.voyaging.configuration.ThymeleafTemplateConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.internal.ObjectArrayElementComparisonStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    public void sendHtmlMessage(String currentUser, Object customer, Object reservation, Object checkin, Object checkout,
                                Object nbRooms, Object roomType, Object price, Object taxes, Object costPerRoom, Object totalCost)
            throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        // set mediaType
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("customer",customer);
        context.setVariable("reservation",reservation);
        context.setVariable("checkin",checkin);
        context.setVariable("checkout",checkout);
        context.setVariable("nbRooms",nbRooms);
        context.setVariable("roomType",roomType);
        context.setVariable("price",price);
        context.setVariable("taxes",taxes);
        context.setVariable("costPerRoom",costPerRoom);
        context.setVariable("totalCost",totalCost);

        helper.setFrom("voyaging2023@gmail.com");
        helper.setTo(currentUser);
        helper.setSubject("Voyaging: Booking Confirmation ");
        String html = templateEngine.process("email-confirmation.html", context);

        log.warn("setting confirmation to true");
        helper.setText(html, true);

        log.info("Sending email to  {} ", currentUser);
        emailSender.send(message);
    }
}
