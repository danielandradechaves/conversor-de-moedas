package service;

import model.DadosConversao;
import model.MoedasSuportadas;
import model.TipoTransacao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conversor {
    private final PegarDados pegarDados = new PegarDados();

    public boolean converterSemTaxa(String moedaOrigem, String moedaDestino, BigDecimal valor) {
        if (verificaConverterSemTaxa(moedaOrigem, moedaDestino, valor)) {
            try {
                DadosConversao conversao = pegarDados.pegarDados(moedaOrigem, moedaDestino);

                BigDecimal cotacao = conversao.cotacao().setScale(4, RoundingMode.HALF_UP);
                BigDecimal valorConvertido = valor.multiply(cotacao).setScale(2, RoundingMode.HALF_UP);
                System.out.println("------------------------------------------------");
                System.out.printf("Conversão de %s para %s:\n", moedaOrigem, moedaDestino);
                System.out.println("Valor original: " + valor + " " + moedaOrigem);
                System.out.println("Valor convertido: " + valorConvertido + " " + moedaDestino);
                System.out.println("------------------------------------------------");
                System.out.println("Cotação: " + cotacao + " " + moedaDestino);
                System.out.println("------------------------------------------------");
                return true;
            }catch (IllegalArgumentException e) {
                //Na AwesomeAPI nem todas as conversões estão disponíveis
                System.out.println("Aviso: " + e.getMessage());
                System.out.println("Tente converter usando uma moeda principal como USD, EUR ou BRL.");
                return false;
            } catch (RuntimeException ex) {
                System.out.println("Erro técnico: " + ex.getMessage());
                return false;
            }
        }
        return false;
    }

    public boolean converterComTaxa(String moedaOrigem, BigDecimal valor, int idTransacao) {
        // Nesse caso a moeda destino sempre será BRL
        String moedaDestino = "BRL";
        if (verificaConverterComTaxa(moedaOrigem, valor, idTransacao)) {
            DadosConversao conversao = pegarDados.pegarDados(moedaOrigem, moedaDestino);

            // manter 4 casas na cotação para não perder a precisão
            BigDecimal cotacao = conversao.cotacao().setScale(4, RoundingMode.HALF_UP);

            // conversão da moeda escolhida para o real(BRL)
            BigDecimal valorConvertido = valor.multiply(cotacao).setScale(2, RoundingMode.HALF_UP);

            TipoTransacao tt = TipoTransacao.buscarPorId(idTransacao);

            // a taxa iof está em porcentagem no TipoTransacao, por isso tem que dividir por 100
            BigDecimal aliquotaIof = tt.getIof().divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);


            BigDecimal imposto = valorConvertido.multiply(aliquotaIof).setScale(2, RoundingMode.HALF_UP);
            BigDecimal valorFinal;
            if (tt.getId() == 4) {
                valorFinal = valorConvertido.subtract(imposto).setScale(2, RoundingMode.HALF_UP);
            } else {
                valorFinal = valorConvertido.add(imposto).setScale(2, RoundingMode.HALF_UP);
            }
            System.out.println("------------------------------------------------");
            System.out.printf("Conversão de %s para %s:\n", moedaOrigem, moedaDestino);
            System.out.println("Valor original: " + valor.setScale(2, RoundingMode.HALF_UP) + " " + moedaOrigem);
            System.out.println("Valor convertido com taxa: " + valorFinal + " " + moedaDestino);
            System.out.println("------------------------------------------------");
            System.out.println("Cotação: " + cotacao + " " + moedaDestino);
            System.out.println("Imposto: " + imposto);
            System.out.println("Alíquota: " + tt.getIof() + "%");
            System.out.println("------------------------------------------------");
            return true;
        }
        return false;
    }

    private boolean verificaConverterSemTaxa(String moedaOrigem,
                                             String moedaDestino,
                                             BigDecimal valor) {
        if (MoedasSuportadas.isSuportada(moedaOrigem) && MoedasSuportadas.isSuportada(moedaDestino)) {
            if(!moedaOrigem.equals(moedaDestino)) {
                if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0) {
                    return true;
                } else {
                    System.out.println("Inclua um valor maior que 0");
                }
            } else {
                System.out.println("selecione moedas diferentes para continuar com a operação");
            }
        } else {
            System.out.println("Uma das moedas não consta na lista de moedas suportadas");
            System.out.println("Moedas suportadas:");
            MoedasSuportadas.exibeListaCodigosDeMoedas();
        }
        return false;
    }

    private boolean verificaConverterComTaxa(String moedaOrigem,
                                             BigDecimal valor,
                                             int idTransacao) {

        if (MoedasSuportadas.isSuportada(moedaOrigem) && !moedaOrigem.equals("BRL")) {
            if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0) {
                if (TipoTransacao.isTransacao(idTransacao)) {
                    return true;
                } else {
                    System.out.println("ID " + idTransacao + " inválido");
                }
            } else {
                System.out.println("Inclua um valor maior que 0");
            }
        } else {
            System.out.println("Uma das moedas não consta na lista de moedas suportadas");
            System.out.println("Moedas suportadas:");
            MoedasSuportadas.exibeListaCodigosDeMoedas();
        }

        return false;
    }

}
