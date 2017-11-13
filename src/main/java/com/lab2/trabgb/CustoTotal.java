package com.lab2.trabgb;

public class CustoTotal {
    private String metodo;
    private double vlrCustoVenda;
    private double vlrCustoMedioUnitario;
    private double vlrCustoEstoque;

    public CustoTotal(String metodo) {
        this.metodo = metodo;
        vlrCustoEstoque = 0;
        vlrCustoMedioUnitario = 0;
        vlrCustoVenda = 0;
    }

    public CustoTotal(String metodo, double vlrCustoVenda, double vlrCustoMedioUnitario, double vlrCustoEstoque) {
        this.metodo = metodo;
        this.vlrCustoVenda = vlrCustoVenda;
        this.vlrCustoMedioUnitario = vlrCustoMedioUnitario;
        this.vlrCustoEstoque = vlrCustoEstoque;
    }

    @Override
    public String toString() {
        return String.format(metodo + " custo de venda: %6.2f    custo médio unitário: %6.2f    custo estoque: %6.2f",vlrCustoVenda,vlrCustoMedioUnitario,vlrCustoEstoque);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustoTotal)) return false;

        CustoTotal that = (CustoTotal) obj;

        if (Double.compare(that.vlrCustoVenda, vlrCustoVenda) != 0) return false;
        if (Double.compare(that.vlrCustoMedioUnitario, vlrCustoMedioUnitario) != 0) return false;
        if (Double.compare(that.vlrCustoEstoque, vlrCustoEstoque) != 0) return false;
        return metodo != null ? metodo.equals(that.metodo) : that.metodo == null;
    }
}
