package ru.mirea.magic.card;

public interface Ability {
    void resolve(CreatureCard source, CreatureCard target);

    void resolve(CreatureCard target);
}
