package com.growthmap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    public record CadastroRequest(String nome, String email) {}
    public record EventoRequest(String nome, String email, String evento, String area, String data) {}

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastro(@RequestBody CadastroRequest req) {
        if (req.email() == null || req.email().isBlank()) {
            return ResponseEntity.badRequest().body("email é obrigatório");
        }
        String html = "<h2>Olá, " + HtmlUtils.htmlEscape(req.nome()) + "!</h2>"
                + "<p>Seu cadastro no Growth Map foi realizado com sucesso.</p>";
        emailService.enviarEmail(req.email(), "Bem-vindo ao Growth Map", html);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/evento")
    public ResponseEntity<String> evento(@RequestBody EventoRequest req) {
        if (req.email() == null || req.email().isBlank()) {
            return ResponseEntity.badRequest().body("email é obrigatório");
        }
        String html = "<h2>Olá, " + HtmlUtils.htmlEscape(req.nome()) + "!</h2>"
                + "<p>Seu evento foi cadastrado no Growth Map:</p>"
                + "<p><b>" + HtmlUtils.htmlEscape(req.evento()) + "</b></p>"
                + "<p>Área: " + HtmlUtils.htmlEscape(req.area())
                + " | Data: " + HtmlUtils.htmlEscape(req.data()) + "</p>";
        emailService.enviarEmail(req.email(), "Evento cadastrado no Growth Map", html);
        return ResponseEntity.accepted().build();
    }
}