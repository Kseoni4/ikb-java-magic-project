package ru.mirea.magic.abilities;

import ru.mirea.magic.card.Ability;
import ru.mirea.magic.card.CreatureCard;

public class FirstStrikeAbility extends Ability {

    public FirstStrikeAbility(String abilityName) {
        super(abilityName);
    }

    @Override
    public void resolve(CreatureCard source, CreatureCard target) {
        source.attack(target);
    }

    @Override
    public void resolve(CreatureCard target) {

    }
}
