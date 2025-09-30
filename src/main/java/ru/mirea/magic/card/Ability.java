package ru.mirea.magic.card;

import java.util.Objects;

public abstract class Ability implements AbilityResolving {

    private final String abilityName;

    public String getAbilityName() {
        return abilityName;
    }

    /**
     * Используем для сравнения объектов способностей между собой
     * @param o объект Ability
     * @return является ли входной объект той же способностью
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ability ability = (Ability) o;
        return Objects.equals(abilityName, ability.abilityName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(abilityName);
    }

    public Ability(String abilityName) {
        this.abilityName = abilityName;
    }
}
