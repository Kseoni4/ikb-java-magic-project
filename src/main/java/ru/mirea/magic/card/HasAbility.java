package ru.mirea.ksenofontov.magic.card;

import java.util.List;

public interface HasAbility {

    List<Ability> getAbilities();

    void addAbility(Ability ability);
}
