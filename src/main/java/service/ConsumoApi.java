package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String pegarDados(String mOrigem, String mDestino) {
        String uri = "https://economia.awesomeapi.com.br/json/last/" + mOrigem + "-" + mDestino;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new IllegalArgumentException("Cotação indisponível para o par " + mOrigem + "-" + mDestino);
            }


            return response.body();

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
