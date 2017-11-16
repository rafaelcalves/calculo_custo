package com.lab2.trabgb;

import com.lab2.trabgb.collections.List;
import com.lab2.trabgb.collections.Queue;
import com.lab2.trabgb.collections.Stack;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.IOException;

public interface IMetodoCusto {
    void loadTransactions();
    void calculateFIFO();
    void calculateLIFO();
    void showCalculations();
    void add(Transacao transaction);
    List<Transacao> getTransactions();
}
