package com.lab2.trabgb;

import com.lab2.trabgb.ADT.List;
import com.lab2.trabgb.ADT.Stack;

import java.io.File;
import java.io.IOException;

public interface IMetodoCusto {
    void load(File file) throws IOException;
    Stack calculateFIFO();
    void calculateLIFO();
    void showCalculations();
    void add(Transacao transaction);
    List<Transacao> getTransactions();
}
