package ru.mirea.ksenofontov.magic.card;

public class CardType {
    private SuperType superType;

    private String type;

    private String subType;

    public CardType(SuperType superType, String type, String subType) {
        this.superType = superType;
        this.type = type;
        this.subType = subType;
    }

    @Override
    public String toString() {
        return superType.toString() +
                " " +
                type +
                " - " +
                subType.trim();
    }
}
