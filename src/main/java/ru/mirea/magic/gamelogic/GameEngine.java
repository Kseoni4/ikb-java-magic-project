package ru.mirea.magic.gamelogic;

import ru.mirea.magic.card.CreatureCard;
import ru.mirea.magic.card.Student;

public interface GameEngine {

    /**
     * Метод для запуска игровой последовательности
     */
    void gameStart();

    /**
     * Метод загрузки игрока из файла с именем 'имя_игрока.txt'. При загрузке игрока загружается его карта.
     * @param playerName имя игрока
     * @return объект Student
     */
    Student playerLoad(String playerName);

    /**
     * Метод для загрузки карты из файла 'имя_карты.txt', согласно формату описания карты.
     * Последовательно создаёт объект карты из составных частей.
     * @param pathToCardFile путь к файлу карты
     * @return объект Card
     */
    CreatureCard cardLoad(String pathToCardFile);
}
