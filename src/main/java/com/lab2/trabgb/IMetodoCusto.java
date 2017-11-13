package com.lab2.trabgb;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IMetodoCusto {
    void load(File file) throws IOException;
    void calculateFIFO();
    void calculateLIFO();
    void showCalculations();
    void add(Transacao transaction);
    List<Transacao> getTransactions();
}
