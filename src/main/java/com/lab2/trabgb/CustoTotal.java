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

    public double getVlrCustoVenda() {
        return vlrCustoVenda;
    }

    public void setVlrCustoVenda(double vlrCustoVenda) {
        this.vlrCustoVenda = vlrCustoVenda;
    }

    public double getVlrCustoMedioUnitario() {
        return vlrCustoMedioUnitario;
    }

    public void setVlrCustoMedioUnitario(double vlrCustoMedioUnitario) {
        this.vlrCustoMedioUnitario = vlrCustoMedioUnitario;
    }

    public double getVlrCustoEstoque() {
        return vlrCustoEstoque;
    }

    public void setVlrCustoEstoque(double vlrCustoEstoque) {
        this.vlrCustoEstoque = vlrCustoEstoque;
    }
}
