package ru.mirea.magic.card;

import java.util.Locale;

public enum SuperType {
    NONE,
    LEGENDARY,
    BASIC,
    ELITE,
    SNOW,
    TOKEN,
    WORLD;

    @Override
    public String toString() {
        if(this.name().equals("NONE")){
            return "";
        }
        return Character.toUpperCase(this.name().charAt(0)) + this.name().substring(1).toLowerCase(Locale.ROOT);
    }
}
