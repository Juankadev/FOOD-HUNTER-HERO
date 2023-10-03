package com.example.ffh_rep.utils;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender {
    private static final String USERNAME = "foodhunterhero@gmail.com";
    private static final String PASSWORD = "Seminario2023";

    public static void sendEmail(String recipient, String subject, String messageText) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String plantillaRegistroExitoso(String name, String email, String password) {
        String htmlContent = "<html><body>";
        htmlContent += "<h1>Bienvenidx a FoodHunterHero</h1>";
        htmlContent += "<p>Hola " + name + ",</p>";
        htmlContent += "<p>Tu registro en FoodHunterHero se ha realizado con éxito. Aquí están tus detalles de inicio de sesión:</p>";
        htmlContent += "<p>User    : " + email + "</p>";
        htmlContent += "<p>Password: " + password + "</p>";
        htmlContent += "<p>Gracias por unirte a nuestra plataforma.</p>";
        htmlContent += "</body></html>";

        return htmlContent;
    }

}
