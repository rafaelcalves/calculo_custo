package com.lab2.trabgb;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MetodoCustoTest {

    IMetodoCusto metodoCusto;

    @Before
    public void prepare(){
       metodoCusto = new MetodoCusto();
    }

    @Test
    public void printMenu(){
        System.out.println("Digite a opção:");
        System.out.println("1 - Carregar transações");
        System.out.println("2 - Mostrar cálculo");
        System.out.println("9 - Sair");
    }

    @Test
    public void loadTest(){
        try {
            String path = System.getProperty("user.dir") + "/files/";
            metodoCusto.load(new File(path + "import.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Transacao> transactions = metodoCusto.getTransactions();

        assertTrue(transactions.get(0).equals(new Transacao("COMPRA",20,1.8)));
        assertTrue(transactions.get(1).equals(new Transacao("COMPRA",80,2)));
        assertTrue(transactions.get(2).equals(new Transacao("COMPRA",50,2.2)));
        assertTrue(transactions.get(3).equals(new Transacao("VENDA",30,0.0)));
        assertTrue(transactions.get(4).equals(new Transacao("VENDA",40,0.0)));
        assertTrue(transactions.get(5).equals(new Transacao("COMPRA",60,1.7)));
        assertEquals(transactions.size(),6);
    }

    @Test
    public void addTest(){

        metodoCusto.add(new Transacao("COMPRA",20,1.8));
        metodoCusto.add(new Transacao("VENDA",30,0.0));

        List<Transacao> transactions = metodoCusto.getTransactions();

        assertTrue(transactions.get(0).equals(new Transacao("COMPRA",20,1.8)));
        assertTrue(transactions.get(1).equals(new Transacao("VENDA",30,0.0)));
    }

}
