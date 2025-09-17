package ru.mirea.magic.card;

public interface Ability {

    /**
     * Разрешает (активирует) способность карты на конкретную цель
     * @param source карта - владелец способности
     * @param target целевая карта
     */
    void resolve(CreatureCard source, CreatureCard target);

    /**
     * Разрешает (активирует) способность карты на саму себя
     * @param target карта - владелец способности
     */
    void resolve(CreatureCard target);
}
