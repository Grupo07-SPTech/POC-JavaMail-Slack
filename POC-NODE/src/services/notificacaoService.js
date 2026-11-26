function postar(caminho, corpo) {
    if (!process.env.EMAIL_API_URL) {
        console.log("EMAIL_API_URL não configurada, e-mail não enviado.");
        return Promise.resolve();
    }

    const baseUrl = process.env.EMAIL_API_URL.replace(/\/+$/, "");
    const rota = caminho.startsWith("/") ? caminho : `/${caminho}`;

    return fetch(`${process.env.EMAIL_API_URL}${caminho}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(corpo),
        signal: AbortSignal.timeout(5000),
    })
        .then(function (resposta) {
            if (!resposta.ok) {
                console.log("Serviço de e-mail respondeu " + resposta.status);
            }
        })
        .catch(function (erro) {
            console.log("Serviço de e-mail indisponível:", erro.message);
        });
}

function avisarCadastro(nome, email) {
    return postar("/api/emails/cadastro", { nome: nome, email: email });
}

function avisarEvento(nome, email, evento, area, data) {
    return postar("/api/emails/evento", {
        nome: nome,
        email: email,
        evento: evento,
        area: area,
        data: data,
    });
}

module.exports = { avisarCadastro, avisarEvento };