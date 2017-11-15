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
            if(transactions.get(i).getTipo() == "COMPRA") {
                fifoQueue.enqueue(transactions.get(i));
                custoTotalFIFO.setVlrCustoEstoque(custoTotalFIFO.getVlrCustoEstoque() + transactions.get(i).getCustoUnitario());
                qtdEstoque += transactions.get(i).getQtde();
            } else {
                Transacao front = fifoQueue.front();
                front.setQtde(front.getQtde() - transactions.get(i).getQtde());
                custoTotalFIFO.setVlrCustoVenda(custoTotalFIFO.getVlrCustoVenda() + front.getCustoUnitario());
                custoTotalFIFO.setVlrCustoEstoque(custoTotalFIFO.getVlrCustoEstoque() - transactions.get(i).getCustoUnitario());
                qtdEstoque -= transactions.get(i).getQtde();
            }
        }
        custoTotalFIFO.setVlrCustoMedioUnitario(custoTotalFIFO.getVlrCustoEstoque()/qtdEstoque);
    }

    @Override
    public Stack<Transacao> calculateLIFO() {
        Stack<Transacao> fifoStack = new StaticStack<>(transactions.numElements());
        for (int i = transactions.numElements() - 1; i >= 0; i--) {
            fifoStack.push(transactions.get(i));
        }
        return fifoStack;

    }

    @Override
    public void showCalculations() {

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
