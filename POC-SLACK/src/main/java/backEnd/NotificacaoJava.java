package backEnd;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NotificacaoJava {
        public static void main(String[] args) throws Exception {
            String token = "xoxb-11996248225009-12022779078544-36Ja378L89dI3fqKUrHaW2GK";
            String channel = "C0BV8BMQ8RG";
            String texto = "Mensagem de teste!";

            String payload = "{\"channel\":\"" + channel + "\",\"text\":\"" + texto + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://slack.com/api/chat.postMessage"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> resposta = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(resposta.body());
        }
    }

