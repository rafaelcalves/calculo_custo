package com.lab2.trabgb;

import com.lab2.trabgb.collections.*;
import com.lab2.trabgb.collections.impl.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MetodoCusto implements IMetodoCusto {
    private SinglyLinkedList<Transacao> transactions;
    private CustoTotal custoTotalFIFO;
    private CustoTotal custoTotalLIFO;
    private Teclado teclado = new Teclado();

    public MetodoCusto() {
        transactions = new SinglyLinkedList<>();
        custoTotalFIFO = new CustoTotal("PEPS");
        custoTotalLIFO = new CustoTotal("UEPS");
    }

    //Responsável por exigir inserção do arquivo desejado
    @Override
    public void loadTransactions() {
        boolean pathOk = false;

        while (!pathOk) {
            File file = defineFile();

            try {
                loadFile(file);
                pathOk = true;
            } catch (IOException e) {
                System.out.println("Caminho ou arquivo inválido.");
            }
        }
    }

    //pede insersão do caminho do arquivo
    private File defineFile() {
        String path = teclado.leString("Digite o caminho do arquivo a ser carregado:");
        return new File(path);
    }

    //carrega o arquivo no buffer
    private void loadFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            transactions = new SinglyLinkedList<>();
            processBufferLines(br);
        }
    }

    //processa o buffer linha a linha
    private void processBufferLines(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] splitLine = line.split(";");
            add(new Transacao(splitLine[1],
                    Integer.parseInt(splitLine[2]),
                    Double.parseDouble(splitLine[3])));
        }
    }

    //copia as transações carregadas enfileirando-as
    @Override
    public void calculateFIFO() {
        custoTotalFIFO = new CustoTotal("PEPS");
        int qtdEstoque = 0;
        Queue<Transacao> fifoQueue = new StaticQueue<>(transactions.numElements());
        for (int i = 0; i < transactions.numElements(); i++) {
            qtdEstoque = processTransactionFIFO(qtdEstoque, fifoQueue, transactions.get(i));
        }
        calculateVlrCustoMedioUnitario(custoTotalFIFO, qtdEstoque);
    }

    //processa as transacoes da fila
    private int processTransactionFIFO(int qtdEstoque, Queue<Transacao> queue, Transacao transaction) {
        if (transaction.getTipo().equals("COMPRA")) {
            queue.enqueue(transaction.clone());
            qtdEstoque += updateCustoTotalWithBuyAndReturnQt(custoTotalFIFO, transaction);
        } else {
            qtdEstoque -= discountFrontBuyFIFO(queue, transaction);
        }
        return qtdEstoque;
    }

    //começa a processar a venda
    private int discountFrontBuyFIFO(Queue<Transacao> queue, Transacao sell) {
        return discountFrontBuyFIFO(queue, sell, 0);
    }

    //faz a recursão do disconto da compra até não haver quantidade pendente na venda
    private int discountFrontBuyFIFO(Queue<Transacao> queue, Transacao sell, int discountedQt) {
        Transacao frontBuy = queue.front();

        if (discountedQt == sell.getQtde())
            return discountedQt;

        discountedQt += setCustoTotalAndReturnDiscountedQtInSell(custoTotalFIFO, frontBuy, sell, discountedQt);

        if (frontBuy.getQtde() == 0)
            queue.dequeue();

        return discountFrontBuyFIFO(queue, sell, discountedQt);
    }

    //copia as transações empilhando-as
    @Override
    public void calculateLIFO() {
        custoTotalLIFO = new CustoTotal("UEPS");
        int qtdEstoque = 0;
        Stack<Transacao> fifoStack = new StaticStack<>(transactions.numElements());
        for (int i = 0; i < transactions.numElements(); i++) {
            qtdEstoque = processTransactionLIFO(qtdEstoque, fifoStack, transactions.get(i));
        }
        calculateVlrCustoMedioUnitario(custoTotalLIFO, qtdEstoque);
    }

    //processa as transações da pilha
    private int processTransactionLIFO(int qtdEstoque, Stack<Transacao> stack, Transacao transaction) {
        if (transaction.getTipo().equals("COMPRA")) {
            stack.push(transaction.clone());
            qtdEstoque += updateCustoTotalWithBuyAndReturnQt(custoTotalLIFO, transaction);
        } else {
            qtdEstoque -= discountFrontBuyLIFO(stack, transaction);
        }
        return qtdEstoque;
    }

    //começa a processar a venda
    private int discountFrontBuyLIFO(Stack<Transacao> stack, Transacao sell) {
        return discountFrontBuyLIFO(stack, sell, 0);
    }

    //faz a recursão do disconto da compra até não haver quantidade pendente na venda
    private int discountFrontBuyLIFO(Stack<Transacao> stack, Transacao sell, int discountedQt) {
        Transacao frontBuy = stack.top();

        if (discountedQt == sell.getQtde())
            return discountedQt;

        discountedQt += setCustoTotalAndReturnDiscountedQtInSell(custoTotalLIFO, frontBuy, sell, discountedQt);

        if (frontBuy.getQtde() == 0)
            stack.pop();

        return discountFrontBuyLIFO(stack, sell, discountedQt);
    }

    //método usado tanto para UEPS quanto para PEPS. Serve para carregar os valores da venda processada no custo total
    private int setCustoTotalAndReturnDiscountedQtInSell(CustoTotal custoTotal, Transacao frontBuy, Transacao sell, int discountedQt) {
        int toDiscountQt = sell.getQtde() - discountedQt;

        if (toDiscountQt > frontBuy.getQtde())
            toDiscountQt = frontBuy.getQtde();

        frontBuy.setQtde(frontBuy.getQtde() - toDiscountQt);
        custoTotal.setVlrCustoVenda(custoTotal.getVlrCustoVenda() + frontBuy.getCustoUnitario() * toDiscountQt);
        custoTotal.setVlrCustoEstoque(custoTotal.getVlrCustoEstoque() - frontBuy.getCustoUnitario() * toDiscountQt);

        return toDiscountQt;
    }

    //calcula o custo médio
    private void calculateVlrCustoMedioUnitario(CustoTotal custoTotal, int qtdEstoque) {
        custoTotal.setVlrCustoMedioUnitario(custoTotal.getVlrCustoEstoque() / qtdEstoque);
    }

    //método usado tanto para UEPS quanto para PEPS. Serve para carregar os valores da compra processada no custo total
    private int updateCustoTotalWithBuyAndReturnQt(CustoTotal custoTotal, Transacao buy) {
        custoTotal.setVlrCustoEstoque(custoTotal.getVlrCustoEstoque() + buy.getCustoUnitario() * buy.getQtde());
        return buy.getQtde();
    }

    //apresenta os cálculos ao usuário conforme solicitado
    @Override
    public void showCalculations() {
        String metodo = "";
        while (!metodo.equals("UEPS") && !metodo.equals("PEPS")) {
            metodo = teclado.leString("Digite o método desejado:");
            switch (metodo){
                case "UEPS":
                    calculateLIFO();
                    System.out.println(custoTotalLIFO);
                    break;
                case "PEPS":
                    calculateFIFO();
                    System.out.println(custoTotalFIFO);
                    break;
                default:
                    System.out.println("Método não encontrado!");
                    break;
            }
        }
    }

    @Override
    public void add(Transacao transaction) {
        transactions.insertLast(transaction);
    }

    @Override
    public List<Transacao> getTransactions() {
        return transactions;
    }

    public CustoTotal getCustoTotalFIFO() {
        return custoTotalFIFO;
    }

    public CustoTotal getCustoTotalLIFO() {
        return custoTotalLIFO;
    }
}
