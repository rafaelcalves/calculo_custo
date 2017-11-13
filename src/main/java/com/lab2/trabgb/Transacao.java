package com.lab2.trabgb;

public class Transacao {
    private String tipo;
    private int qtde;
    private double custoUnitario;

    public Transacao(String tipo, int qtde, double custoUnitario) {
        this.tipo = tipo;
        this.qtde = qtde;
        this.custoUnitario = custoUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transacao)) return false;

        Transacao transacao = (Transacao) o;

        if (qtde != transacao.qtde) return false;
        if (Double.compare(transacao.custoUnitario, custoUnitario) != 0) return false;
        return tipo != null ? tipo.equals(transacao.tipo) : transacao.tipo == null;
    }
}