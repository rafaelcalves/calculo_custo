package com.lab2.trabgb;

import com.lab2.trabgb.ADT.*;
import com.lab2.trabgb.ADT.impl.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MetodoCusto implements IMetodoCusto {
    public static final int DEFAULT_MAX_SIZE = 100;
    private List<Transacao> trasactions;
    private CustoTotal custoTotalFIFO;
    private CustoTotal custoTotalLIFO;

    public MetodoCusto() {
        trasactions = new StaticList<>(DEFAULT_MAX_SIZE);
        custoTotalFIFO = new CustoTotal("PEPS");
        custoTotalLIFO = new CustoTotal("UEPS");
    }

    @Override
    public void load(File file) throws IOException {
        trasactions = new StaticList<>(countTransactions(file));
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

    private int countTransactions(File file) throws IOException {
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                ++counter;
            }
        }
        return counter;
    }

    @Override
    public Stack<Transacao> calculateFIFO() {
        Stack<Transacao> fifoStack = new StaticStack<>(trasactions.numElements());
        for (int i = trasactions.numElements() - 1; i >= 0; i--) {
            fifoStack.push(trasactions.get(i));
        }
        return fifoStack;
    }

    @Override
    public void calculateLIFO() {

    }

    @Override
    public void showCalculations() {

    }

    @Override
    public void add(Transacao transaction) {
        trasactions.insert(transaction,0);
    }

    @Override
    public List<Transacao> getTransactions() {
        return trasactions;
    }

}
