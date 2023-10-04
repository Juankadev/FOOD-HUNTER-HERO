package com.example.ffh_rep.utils;

import android.os.AsyncTask;
import android.util.Log;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSenderTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "EmailSenderTask";

    private final String username;
    private final String password;
    private final String recipientEmail;
    private final String subject;
    private final String messageBody;

    public EmailSenderTask(String recipientEmail, String subject, String messageBody) {
        this.username = "sendertoreset@gmail.com";
        this.password = "irup orpk ltfk kwxw";
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.messageBody = messageBody;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            message.setContent(messageBody, "text/html");


            Transport.send(message);

            Log.d(TAG, "Correo electrónico enviado con éxito");
            return true;
        } catch (MessagingException e) {
            Log.e(TAG, "Error al enviar el correo electrónico", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        Log.d("EMAIL", "ENVIADo");
    }
}
