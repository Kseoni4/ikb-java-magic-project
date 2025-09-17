package ru.mirea.ksenofontov.magic.card;

public interface Ability {
    int getPriority();

    CreatureCard getTarget();

    void setTarget(CreatureCard target);

    void activate();
}
