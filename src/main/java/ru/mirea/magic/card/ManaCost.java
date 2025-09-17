package ru.mirea.magic.card;

import java.util.EnumMap;
import java.util.Map;

public class ManaCost {
    private int genericMana;
    private final Map<ManaType, Integer> coloredMana;

    public ManaCost(int genericMana) {
        this.genericMana = genericMana;
        this.coloredMana = new EnumMap<>(ManaType.class);
    }

    public void addColoredMana(ManaType manaType, int count){
        coloredMana.put(manaType, count);
    }

    public void setGenericMana(int genericMana) {
        this.genericMana = genericMana;
    }

    public int getGenericMana() {
        return genericMana;
    }

    public Map<ManaType, Integer> getColoredMana() {
        return coloredMana;
    }

    @Override
    public String toString() {
        StringBuilder coloredManaLine = new StringBuilder();

        for (ManaType manaType : coloredMana.keySet()) {
            coloredManaLine
                    .append(String.valueOf(manaType)
                            .repeat(Math.max(0, coloredMana.get(manaType)))
                    );
        }

        return "{"+genericMana+"}"+coloredManaLine;
    }
}