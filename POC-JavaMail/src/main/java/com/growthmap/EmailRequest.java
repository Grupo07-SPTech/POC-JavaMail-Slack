package com.growthmap;

public record EmailRequest(
        String emailDestinatario,
        String assunto,
        String conteudo
) {}