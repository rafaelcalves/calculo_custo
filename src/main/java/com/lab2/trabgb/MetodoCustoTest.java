package com.lab2.trabgb;

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
        metodoCusto.loadTransactions();
    }

    private static void mostrarCalculos() {
        metodoCusto.showCalculations();
    }
}
