package model;

import java.util.Arrays;

public enum MoedasSuportadas {
    AED("Dirham dos Emirados"),
    AMD("Dram Armênio"),
    AOA("Kwanza Angolano"),
    ARS("Peso Argentino"),
    AUD("Dólar Australiano"),
    BBD("Dólar de Barbados"),
    BDT("Taka de Bangladesh"),
    BGN("Lev Búlgaro"),
    BOB("Boliviano"),
    BRL("Real Brasileiro"),
    BSD("Dólar das Bahamas"),
    BYN("Rublo Bielorrusso"),
    CAD("Dólar Canadense"),
    CHF("Franco Suíço"),
    CLP("Peso Chileno"),
    EUR("Euro"),
    USD("Dólar Americano");

    private final String descricao;

    MoedasSuportadas(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static boolean isSuportada(String codigo) {
        if (codigo.isEmpty()) {
            return false;
        }
        try {
            MoedasSuportadas.valueOf(codigo);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void exibeListaCodigosDeMoedas() {
        Arrays.stream(MoedasSuportadas.values()).forEach(m -> {
            if (!m.equals(MoedasSuportadas.BRL)) {
                System.out.println(m + " - " + m.getDescricao());
            }
        });
    }
}
