package ru.mirea.magic.abilities;

import ru.mirea.magic.card.Ability;
import ru.mirea.magic.card.CreatureCard;

public class FlyingAbility extends Ability {

    public FlyingAbility(String abilityName) {
        super(abilityName);
    }

    @Override
    public void resolve(CreatureCard source, CreatureCard target) {

    }

    @Override
    public void resolve(CreatureCard target) {

    }
}
