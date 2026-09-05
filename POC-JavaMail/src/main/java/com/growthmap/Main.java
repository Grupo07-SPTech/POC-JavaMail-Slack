package com.growthmap;

public class Main {
    public static void main(String[] args) {
        EmailService emailService = new EmailService();

        emailService.enviarEmail(
                "vitor.machado.raimundo@gmail.com",
                "Teste com HTML",
                """
                        <html>
                            <body>
                                <h1>Bem-Vindo!</h1>
                                
                                <p>
                                    Olá! Seu cadastro foi realizado
                                    com sucesso.
                                </p>
                                
                                <p>
                                    Agora você já pode utilizar
                                    o sistema.
                                </p>
                            </body>
                        </html>
                        """
        );
    }
}