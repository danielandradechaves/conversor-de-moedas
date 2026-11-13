package model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosConversao(@JsonAlias("bid") BigDecimal cotacao,
                             @JsonAlias("create_date") String data) {
}
