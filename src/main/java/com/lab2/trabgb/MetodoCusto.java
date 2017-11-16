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

    public MetodoCusto() {
        transactions = new SinglyLinkedList<>();
        custoTotalFIFO = new CustoTotal("PEPS");
        custoTotalLIFO = new CustoTotal("UEPS");
    }

    @Override
    public void load(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split(";");
                add(new Transacao(splitLine[1],
                        Integer.parseInt(splitLine[2]),
                        Double.parseDouble(splitLine[3])));
            }
        }
    }

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

    private int processTransactionFIFO(int qtdEstoque, Queue<Transacao> queue, Transacao transaction) {
        if (transaction.getTipo() == "COMPRA") {
            queue.enqueue(transaction);
            qtdEstoque += updateCustoTotalWithBuyAndReturnQt(custoTotalFIFO, transaction);
        } else {
            qtdEstoque -= discountFrontBuyFIFO(queue, transaction);
        }
        return qtdEstoque;
    }

    private void calculateVlrCustoMedioUnitario(CustoTotal custoTotal, int qtdEstoque) {
        custoTotal.setVlrCustoMedioUnitario(custoTotal.getVlrCustoEstoque() / qtdEstoque);
    }

    private int updateCustoTotalWithBuyAndReturnQt(CustoTotal custoTotal, Transacao buy) {
        custoTotal.setVlrCustoEstoque(custoTotal.getVlrCustoEstoque() + buy.getCustoUnitario() * buy.getQtde());
        return buy.getQtde();
    }

    private int discountFrontBuyFIFO(Queue<Transacao> queue, Transacao sell) {
        return discountFrontBuyFIFO(queue, sell, 0);
    }

    private int discountFrontBuyFIFO(Queue<Transacao> queue, Transacao sell, int discountedQt) {
        Transacao frontBuy = queue.front();

        if (discountedQt == sell.getQtde())
            return discountedQt;

        discountedQt += setCustoTotalAndReturnDiscountedQtInSell(custoTotalFIFO, frontBuy, sell, discountedQt);

        if (frontBuy.getQtde() == 0)
            queue.dequeue();

        return discountFrontBuyFIFO(queue, sell, discountedQt);
    }

    private int setCustoTotalAndReturnDiscountedQtInSell(CustoTotal custoTotal, Transacao frontBuy, Transacao sell, int discountedQt) {
        int toDiscountQt = sell.getQtde() - discountedQt;

        if (toDiscountQt > frontBuy.getQtde())
            toDiscountQt = frontBuy.getQtde();

        frontBuy.setQtde(frontBuy.getQtde() - toDiscountQt);
        custoTotal.setVlrCustoVenda(custoTotal.getVlrCustoVenda() + frontBuy.getCustoUnitario() * toDiscountQt);
        custoTotal.setVlrCustoEstoque(custoTotal.getVlrCustoEstoque() - frontBuy.getCustoUnitario() * toDiscountQt);

        return toDiscountQt;
    }

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

    private int processTransactionLIFO(int qtdEstoque, Stack<Transacao> stack, Transacao transaction) {
        if (transaction.getTipo() == "COMPRA") {
            stack.push(transaction);
            qtdEstoque += updateCustoTotalWithBuyAndReturnQt(custoTotalLIFO, transaction);
        } else {
            qtdEstoque -= discountFrontBuyLIFO(stack, transaction);
        }
        return qtdEstoque;
    }


    private int discountFrontBuyLIFO(Stack<Transacao> stack, Transacao sell) {
        return discountFrontBuyLIFO(stack, sell, 0);
    }

    private int discountFrontBuyLIFO(Stack<Transacao> stack, Transacao sell, int discountedQt) {
        Transacao frontBuy = stack.top();

        if (discountedQt == sell.getQtde())
            return discountedQt;

        discountedQt += setCustoTotalAndReturnDiscountedQtInSell(custoTotalLIFO, frontBuy, sell, discountedQt);

        if (frontBuy.getQtde() == 0)
            stack.pop();

        return discountFrontBuyLIFO(stack, sell, discountedQt);
    }

    @Override
    public void showCalculations() {
        System.out.println(custoTotalFIFO);
        System.out.println(custoTotalLIFO);
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
