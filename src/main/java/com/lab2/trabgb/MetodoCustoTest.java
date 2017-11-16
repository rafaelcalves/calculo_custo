package com.lab2.trabgb;


import java.io.File;
import java.io.IOException;

public class MetodoCustoTest {
    private static MetodoCusto metodoCusto = new MetodoCusto();
    private static Teclado teclado = new Teclado();

    public static void main(String[] args) {
        int opcao = 0;

        while (opcao != 9) {
            printMenu();
            opcao = teclado.leInt("Digite a opção desejada: ");
            System.out.print("\n");
            switch (opcao) {
                case 1:
                    carregarTransacoes();
                    break;
                case 2:
                    mostrarCalculos();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Digite a opção:");
        System.out.println("1 - Carregar transações");
        System.out.println("2 - Mostrar cálculo");
        System.out.println("9 - Sair\n");
    }

    private static void carregarTransacoes() {
        boolean pathOk = false;

        while (!pathOk) {
            String path = teclado.leString("Digite o caminho do arquivo a ser carregado:");

            if (path.equals("my"))
                path = System.getProperty("user.dir") + "/files/import.csv";

            File file = new File(path);

            try {
                metodoCusto.load(file);
                pathOk = true;
            } catch (IOException e) {
                System.out.println("Caminho ou arquivo inválido.");
            }
        }
    }

    private static void mostrarCalculos() {
        String metodo = "";
        while (!metodo.equals("UEPS") && !metodo.equals("PEPS")) {
            metodo = teclado.leString("Digite o método desejado:");
            switch (metodo){
                case "UEPS":
                    metodoCusto.showCalculations();
                    break;
                case "PEPS":
                    metodoCusto.showCalculations();
                    break;
                default:
                    System.out.println("Método não encontrado!");
                    break;
            }
        }
    }
}
