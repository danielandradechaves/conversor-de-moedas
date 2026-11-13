package model;

import java.math.BigDecimal;
import java.util.Arrays;

public enum TipoTransacao {
    MOEDA_ESPECIE(new BigDecimal("3.5"), 1, "1 - Compra ou saque de moeda em espécie"),
    CARTAO_INTERNACIONAL(new BigDecimal("3.5"), 2, "2 - Compras internacionais com cartão de crédito/débito"),
    REMESSA_INVESTIMENTO(new BigDecimal("1.1"), 3, "3 - Investimento no mercado financeiro de capitais no exterior"),
    RECEBIMENTO_EXTERIOR(new BigDecimal("0.38"), 4, "4 - Recebimentos do exterior");

    private final BigDecimal iof;
    private final int id;
    private final String descricao;

    TipoTransacao(BigDecimal iof, int id, String descricao) {
        this.iof = iof;
        this.id = id;
        this.descricao = descricao;
    }

    public BigDecimal getIof() {
        return iof;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static boolean isTransacao(int id) {
        for (TipoTransacao transacao: TipoTransacao.values()) {
            if(transacao.id == id) {
                return true;
            }
        }

        return false;
    }

    public  static TipoTransacao buscarPorId(int id) {
        for (TipoTransacao transacao: TipoTransacao.values()) {
            if (transacao.id == id) {
                return transacao;
            }
        }
        throw new IllegalArgumentException("ID " + id + " inválido");
    }

    public static void exibeListaTipoTransacao() {
        Arrays.stream(TipoTransacao.values()).forEach(t -> System.out.println(t.getDescricao()));
    }
}
