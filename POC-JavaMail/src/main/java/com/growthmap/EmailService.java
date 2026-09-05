package com.growthmap;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class EmailService {

    private Properties carregarConfiguracoes() {

        Properties configuracoes = new Properties();

        try (InputStream arquivo =
                     getClass()
                             .getClassLoader()
                             .getResourceAsStream("application.properties")) {

            if (arquivo == null) {
                throw new RuntimeException(
                        "Arquivo application.properties não encontrado."
                );
            }

            configuracoes.load(arquivo);

            return configuracoes;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao carregar as configurações.",
                    e
            );
        }
    }

    public void enviarEmail(
            String emailDestinatario,
            String assunto,
            String conteudo
    ) {

        Properties configuracoes = carregarConfiguracoes();

        String emailRemetente =
                configuracoes.getProperty("email.remetente");

        String senhaApp =
                configuracoes.getProperty("email.senha");

        Properties propriedades = new Properties();

        propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.port", "587");
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(
                propriedades,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                emailRemetente,
                                senhaApp
                        );
                    }
                }
        );

        try {

            Message mensagem = new MimeMessage(session);

            mensagem.setFrom(
                    new InternetAddress(emailRemetente)
            );

            mensagem.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailDestinatario)
            );

            mensagem.setSubject(assunto);

            mensagem.setContent(conteudo, "text/html; charset=UTF-8");

            Transport.send(mensagem);

            System.out.println("E-mail enviado com sucesso!");

        } catch (Exception e) {

            System.out.println("Erro ao enviar e-mail.");
            e.printStackTrace();

        }
    }
}