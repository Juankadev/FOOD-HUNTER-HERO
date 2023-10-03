package com.example.ffh_rep.utils;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender extends AsyncTask<Void, Void, Boolean> {
    //Live
    private static final String USERNAME = "pcaero_programacion@hotmail.com";
    private static final String PASSWORD = "123698745PROGRAMACION";

    //Gmail
    /*private static final String USERNAME = "foodhunterhero@gmail.com";
    private static final String PASSWORD = "Seminario2023";*/

    private final String recipient;
    private final String subject;
    private final String messageText;
    private final EmailSendListener listener;

    public interface EmailSendListener {
        void onEmailSendSuccess();
        void onEmailSendError();
    }

    public EmailSender(String recipient, String subject, String messageText, EmailSendListener listener) {
        this.recipient = recipient;
        this.subject = subject;
        this.messageText = messageText;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //Gmail
        /*Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");*/

        //Live
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.live.com");
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
            return true; // Envío exitoso
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // Error en el envío
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            if (listener != null) {
                listener.onEmailSendSuccess();
            }
        } else {
            if (listener != null) {
                listener.onEmailSendError();
            }
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
