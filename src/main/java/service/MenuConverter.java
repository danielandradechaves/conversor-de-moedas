package service;

import model.TipoTransacao;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuConverter {
    private final Scanner scanner = new Scanner(System.in);
    private final Conversor conversor = new Conversor();

    private String moedaOrigem;
    private String moedaDestino;
    private BigDecimal valor;
    private int idTransacao;

    private boolean convertido = false;

    public void menuConverterSemTaxa() {
        System.out.println("OBS: Essa opção permite conversões entre moedas estrangeiras.");
        do {
            moedaOrigem = pegaMoedaOrigem();
            moedaDestino = pegaMoedaDestino();
            valor = pegaValor(moedaOrigem);

            convertido = conversor.converterSemTaxa(moedaOrigem, moedaDestino, valor);
        } while (!convertido);
        moedaOrigem = "";
        moedaDestino = "";
        valor = null;
    }

    public void menuConverterComTaxa() {
        System.out.println("OBS: Essa operação só permite fazer a conversão de moedas estrangeiras para real(BRL)");
        do {
            idTransacao = pegaIdTransicao();
            moedaOrigem = pegaMoedaOrigem();
            valor = pegaValor(moedaOrigem);
            try {
                convertido = conversor.converterComTaxa(moedaOrigem, valor, idTransacao);
            } catch (InputMismatchException e) {
                System.out.println("Essa operação só aceita valores monetários");
            }


        } while (!convertido);
        moedaOrigem = "";
        moedaDestino = "";
        valor = null;
        idTransacao = -1;
    }

    private String pegaMoedaOrigem() {
        System.out.print("Digite o código da moeda de origem: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    private String pegaMoedaDestino() {
        System.out.print("Digite o código da moeda de destino: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    private BigDecimal pegaValor(String moedaO) {
        System.out.print("Digite o valor a ser convertido: " + moedaO + " ");
        String entrada = scanner.nextLine().trim().replace(".", ",");
        try {
            return new BigDecimal(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Essa operação só aceita valores monetários válidos (ex: 10.50 ou 10,50).");
        }
        return null;
    }

    private int pegaIdTransicao() {
        System.out.println("Escolha o tipo de transação: ");
        TipoTransacao.exibeListaTipoTransacao();
        String entrada = scanner.nextLine().trim();
        try {
            return Integer.parseInt(entrada);
        } catch (InputMismatchException e) {
            System.out.println("Essa operação só aceita números inteiros");
        }

        return -1;
    }
}
