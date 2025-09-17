package ru.mirea.magic.card;

import java.util.List;

public interface HasAbility {

    List<Ability> getAbilities();

    void addAbility(Ability ability);
}
