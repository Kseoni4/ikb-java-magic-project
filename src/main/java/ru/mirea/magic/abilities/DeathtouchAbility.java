package ru.mirea.magic.abilities;

import ru.mirea.magic.card.Ability;
import ru.mirea.magic.card.CreatureCard;

public class DeathtouchAbility extends Ability {

    public DeathtouchAbility(String abilityName) {
        super(abilityName);
    }

    @Override
    public void resolve(CreatureCard source, CreatureCard target) {
        target.changeToughness(-target.getToughness());
    }

    @Override
    public void resolve(CreatureCard target) {

    }
}
