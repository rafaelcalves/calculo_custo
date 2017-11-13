package com.lab2.trabgb;

import com.lab2.trabgb.IMetodoCusto;
import com.lab2.trabgb.Transacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MetodoCusto implements IMetodoCusto {
    private List<Transacao> trasactions;
    private CustoTotal custoTotalFIFO;
    private CustoTotal custoTotalLIFO;

    public MetodoCusto() {
        trasactions = new ArrayList<>();
        custoTotalFIFO = new CustoTotal("PEPS");
        custoTotalLIFO = new CustoTotal("UEPS");
    }

    @Override
    public void load(File file) throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {
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
        //Stack<Transacao> = new Sta
    }

    @Override
    public void calculateLIFO() {

    }

    @Override
    public void showCalculations() {

    }

    @Override
    public void add(Transacao transaction) {
        trasactions.add(transaction);
    }

    @Override
    public List<Transacao> getTransactions() {
        return trasactions;
    }

    public
}
