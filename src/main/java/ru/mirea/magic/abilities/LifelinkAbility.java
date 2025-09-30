package ru.mirea.magic.abilities;

import ru.mirea.magic.card.Ability;
import ru.mirea.magic.card.CreatureCard;

public class LifelinkAbility extends Ability {

    public LifelinkAbility(String abilityName) {
        super(abilityName);
    }

    @Override
    public void resolve(CreatureCard source, CreatureCard target) {
        int power = source.getPower();
        source.changeToughness(power);
    }

    @Override
    public void resolve(CreatureCard target) {

    }
}
