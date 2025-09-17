package ru.mirea.magic.card;

public abstract class Card {
    private String name;

    private ManaCost manaCost;

    private CardType cardType;

    private String setTitle;

    private String oracleText;

    public Card(String name, ManaCost manaCost, CardType cardType, String setTitle, String oracleText) {
        this.name = name;
        this.manaCost = manaCost;
        this.cardType = cardType;
        this.setTitle = setTitle;
        this.oracleText = oracleText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManaCost getManaCost() {
        return manaCost;
    }

    public void setManaCost(ManaCost manaCost) {
        this.manaCost = manaCost;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getSetTitle() {
        return setTitle;
    }

    public void setSetTitle(String setTitle) {
        this.setTitle = setTitle;
    }

    public String getOracleText() {
        return oracleText;
    }

    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", manaCost=" + manaCost +
                ", cardType=" + cardType +
                ", setTitle='" + setTitle + '\'' +
                ", oracleText='" + oracleText + '\'' +
                '}';
    }
}
