package ru.mirea.magic.gamelogic;

import ru.mirea.magic.card.CreatureCard;
import ru.mirea.magic.card.Student;

public interface GameDisplayContext {

    /**
     * Вывести на экран данные о повреждении существ
     * @param attacker атакующее существо
     * @param target целевое существо
     */
    void showDamageTarget(CreatureCard attacker, CreatureCard target);

    /**
     * Вывести игрока - победителя
     * @param winner игрок, победивший в бою
     */
    void showPlayerWinner(Student winner);


    /**
     * Вывести текущую фазу боя
     * @param phase название фазы боя
     */
    void showCurrentPhase(String phase);

    /**
     * Вывод текста на экран.
     * Может реализовывать отображение через стандартный вывод на экран System.out.println().
     * @param text текст
     */
    void displayText(String text);
}
