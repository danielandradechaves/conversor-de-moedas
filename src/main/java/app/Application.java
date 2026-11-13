package app;

import model.MoedasSuportadas;
import service.MenuConverter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    private final Scanner scanner = new Scanner(System.in);
    private final MenuConverter menuConverter = new MenuConverter();

    public void execute() {
        int opcao;
        do {
            System.out.println("""
                    *********************************************
                     Bem vindo ao conversor de moedas!
                     Selecione uma opção:
                    
                     0 - Sair
                     1 - Exibir lista de moedas suportadas
                     2 - Conversão sem taxa
                     3 - Conversão com taxa
                    *********************************************
                    """);
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                processarOpcao(opcao);
            }catch (InputMismatchException e) {
                System.out.println("essa operação só aceita números inteiros");
            }
        } while (true);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 0 -> System.exit(0);
            case 1 -> MoedasSuportadas.exibeListaCodigosDeMoedas();
            case 2 -> menuConverter.menuConverterSemTaxa();
            case 3 -> menuConverter.menuConverterComTaxa();
        }
    }
}
