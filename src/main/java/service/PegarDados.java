package service;

import model.DadosConversao;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class PegarDados {
    private final ConsumoApi api = new ConsumoApi();

    public DadosConversao pegarDados(String moedaOrigem, String moedaDestino) {
        String responseBody = api.pegarDados(moedaOrigem, moedaDestino);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, DadosConversao> mapConversao = mapper
                .readValue(responseBody, new TypeReference<>() {
                });

        return mapConversao.values().iterator().next();
    }
}
