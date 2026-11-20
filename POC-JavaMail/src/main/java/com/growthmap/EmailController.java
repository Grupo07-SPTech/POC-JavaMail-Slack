package com.growthmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/enviar")
    public String enviarEmail() {
        try {
            emailService.enviarEmail(
                    "giovannaberaldovieira@gmail.com",
                    "Teste do Navegador",
                    "<h1>Deu certo!</h1>"
            );
            return "E-mail enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }
}