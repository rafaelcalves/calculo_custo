package com.lab2.trabgb;

import com.lab2.trabgb.collections.List;
import com.lab2.trabgb.collections.Queue;
import com.lab2.trabgb.collections.Stack;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MetodoCustoTest {

    private Transacao TRANSACTION_1 = new Transacao("COMPRA", 20, 1.8);
    private Transacao TRANSACTION_2 = new Transacao("COMPRA", 80, 2);
    private Transacao TRANSACTION_3 = new Transacao("COMPRA", 50, 2.2);
    private Transacao TRANSACTION_4 = new Transacao("VENDA", 30, 0.0);
    private Transacao TRANSACTION_5 = new Transacao("VENDA", 40, 0.0);
    private Transacao TRANSACTION_6 = new Transacao("COMPRA", 60, 1.7);

    MetodoCusto metodoCusto;

    @Before
    public void prepare(){
       metodoCusto = new MetodoCusto();
        TRANSACTION_1 = new Transacao("COMPRA", 20, 1.8);
        TRANSACTION_2 = new Transacao("COMPRA", 80, 2);
        TRANSACTION_3 = new Transacao("COMPRA", 50, 2.2);
        TRANSACTION_4 = new Transacao("VENDA", 30, 0.0);
        TRANSACTION_5 = new Transacao("VENDA", 40, 0.0);
        TRANSACTION_6 = new Transacao("COMPRA", 60, 1.7);
    }

    @Test
    public void printMenu(){
        System.out.println("Digite a opção:");
        System.out.println("1 - Carregar transações");
        System.out.println("2 - Mostrar cálculo");
        System.out.println("9 - Sair");
    }

    @Test
    public void loadTransactionsFromFile() throws IOException{

        String path = System.getProperty("user.dir") + "/files/";
        metodoCusto.load(new File(path + "import.csv"));

        List<Transacao> transactions = metodoCusto.getTransactions();

        assertTrue(transactions.get(0).equals(TRANSACTION_1));
        assertTrue(transactions.get(1).equals(TRANSACTION_2));
        assertTrue(transactions.get(2).equals(TRANSACTION_3));
        assertTrue(transactions.get(3).equals(TRANSACTION_4));
        assertTrue(transactions.get(4).equals(TRANSACTION_5));
        assertTrue(transactions.get(5).equals(TRANSACTION_6));
        assertEquals(transactions.numElements(),6);
    }

    @Test
    public void addTwoTransactionsToTransactionsList(){

        metodoCusto.add(TRANSACTION_1);
        metodoCusto.add(TRANSACTION_4);

        List<Transacao> transactions = metodoCusto.getTransactions();

        assertTrue(transactions.get(0).equals(TRANSACTION_1));
        assertTrue(transactions.get(1).equals(TRANSACTION_4));
    }

    @Test
    public void canCallCalculateFIFO(){
        metodoCusto.add(TRANSACTION_1);
        metodoCusto.add(TRANSACTION_2);
        metodoCusto.add(TRANSACTION_3);
        metodoCusto.add(TRANSACTION_4);
        metodoCusto.add(TRANSACTION_5);
        metodoCusto.add(TRANSACTION_6);

        metodoCusto.calculateFIFO();

        assertTrue(136 == metodoCusto.getCustoTotalFIFO().getVlrCustoVenda());
        assertTrue(272 == metodoCusto.getCustoTotalFIFO().getVlrCustoEstoque());
        assertTrue(1.94 == metodoCusto.getCustoTotalFIFO().getVlrCustoMedioUnitario());
    }

    @Test
    public void canCallCalculateLIFO(){
        metodoCusto.add(TRANSACTION_1);
        metodoCusto.add(TRANSACTION_2);
        metodoCusto.add(TRANSACTION_3);
        metodoCusto.add(TRANSACTION_4);
        metodoCusto.add(TRANSACTION_5);
        metodoCusto.add(TRANSACTION_6);

        Stack<Transacao> stackLifo = metodoCusto.calculateLIFO();

        assertTrue(stackLifo.pop().equals(TRANSACTION_6));
        assertTrue(stackLifo.pop().equals(TRANSACTION_5));
        assertTrue(stackLifo.pop().equals(TRANSACTION_4));
        assertTrue(stackLifo.pop().equals(TRANSACTION_3));
        assertTrue(stackLifo.pop().equals(TRANSACTION_2));
        assertTrue(stackLifo.pop().equals(TRANSACTION_1));
    }

    @Test
    public void canCallShowCalculations(){
        metodoCusto.showCalculations();
    }

}
