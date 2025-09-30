package ru.mirea.magic.card;


public interface AbilityResolving {

    /**
     * Вызывается, если способность направлена на цель от источника
     * @param source источник способности (владелец как правило)
     * @param target целевое существо
     */
    void resolve(CreatureCard source, CreatureCard target);

    /**
     * Вызывается, если способность направлена на сам источник (владельца)
     * @param target владелец способности
     */
    void resolve(CreatureCard target);
}
