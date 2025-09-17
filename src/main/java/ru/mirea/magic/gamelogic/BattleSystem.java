package ru.mirea.magic.gamelogic;

import ru.mirea.magic.card.Student;

public interface BattleSystem {

    /**
     * Метод загружает игроков в словарь PlayerCard, где ключ - объект Student, а значение - его карточка.
     * @param playerOne - первый игрок
     * @param playerTwo - второй игрок
     */
    void prepareMatch(Student playerOne, Student playerTwo);

    /**
     * Начинает матч, инициализирует BattleContext в конструктор отдаёт карты игроков.
     * Матч идёт в цикле, пока у одной из карты свойство toughness не примет значение 0 и ниже.
     * Каждая итерация цикла состоит из трёх фаз, вызываемых методами BattleContext:<br>
     * <ol>
     *     <li>Фаза атаки - выбирается атакующая карта и наносит урон другой</li>
     *     <li>Фаза блокирования - получившая урон карта наносит урон атакующей</li>
     *     <li>Фаза проверки здоровья - выбирается победившая карта</li>
     * </ol>
     * После последней фазы цикл заканчивается в случае, если фаза проверки здоровья вернула не null значение.
     * @return объект Student - победивший игрок.
     */
    Student startBattle();
}
