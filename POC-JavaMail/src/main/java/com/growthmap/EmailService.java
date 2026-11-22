package com.growthmap;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Value("${email.remetente}")
    private String emailRemetente;

    @Value("${email.senha}")
    private String senhaApp;

    public void enviarEmail(String emailDestinatario, String assunto, String conteudo) {
        Properties propriedades = new Properties();
        propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.port", "587");
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(propriedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailRemetente, senhaApp);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(emailRemetente));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestinatario));
            mensagem.setSubject(assunto);
            mensagem.setContent(conteudo, "text/html; charset=UTF-8");

            Transport.send(mensagem);
            System.out.println("E-mail enviado com sucesso!");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail.", e);
        }
    }
}