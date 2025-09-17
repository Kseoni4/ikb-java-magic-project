package ru.mirea.ksenofontov.magic.card;

public enum ManaType {
    WHITE("W"),
    BLUE("U"),
    BLACK("B"),
    RED("R"),
    GREEN("G"),
    COLORLESS("C");

    private final String symbol;

    public String getSymbol() {
        return symbol;
    }

    ManaType(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "{"+symbol+"}";
    }
}
