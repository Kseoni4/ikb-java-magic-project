package ru.mirea.hidden.tests;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import ru.mirea.magic.*;
import ru.mirea.magic.card.CreatureCard;
import ru.mirea.magic.gamelogic.BattleContext;
import ru.mirea.magic.gamelogic.BattleSystem;
import ru.mirea.magic.gamelogic.GameEngine;
import ru.mirea.magic.gamelogic.GameIO;

@Tag("game-logic-tests")
@DisplayName("Тестирование игрового функционала")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameLogicTest {
    private static String userSubfolder;

    private final Map<String, Class<?>[]> requiredImplements =
            Map.of(
                    "GameEngineImpl", new Class[]{GameIO.class, BattleSystem.class},
                    "BattleSystemImpl", new Class[]{BattleContext.class},
                    "BattleContextImpl", new Class[]{},
                    "GameIOImpl", new Class[]{}
            );
    @BeforeAll
    @DisplayName("Получаем пользовательский пакет внутри ru.mirea.magic")
    static void getUserPackageSubfolder() {
        System.out.println(">>Получение пользовательского пакета внутри ru.mirea.magic<<");

        File packageDir = FilePathUtils.getPackageDir();

        Assertions.assertNotEquals(0, packageDir.listFiles().length, "❌ Директория пуста");

        File userSubfolderDir = FilePathUtils.extractUserSubfolderDir(packageDir.listFiles());

        Assertions.assertNotNull(userSubfolderDir, "❌ Директория студента не найдена");

        userSubfolder = userSubfolderDir.getName();

        System.out.printf("✅ Пакет найден: %s%n", userSubfolder);
    }

    @Test
    @Order(1)
    void implementationsExists() {
        System.out.println(">>Проверка наличия реализаций классов<<");

        for (String className : requiredImplements.keySet()) {
            System.out.printf("Проверка: %s%n", className);
            Assertions.assertDoesNotThrow(() -> {
                Class<?>[] parameters = requiredImplements.get(className);

                Object[] params = new Object[parameters.length];

                ReflectionTestUtils.getClass("magic." + userSubfolder, className).getDeclaredConstructor(parameters).newInstance(params);
                System.out.printf("✅Реализация %s найдена%n", className);
            }, "❌ Реализация %s отсутствует".formatted(className));
        }
    }

    @Test
    @Order(2)
    @DisplayName("Проверка создания карты из тестового файла")
    void createCardTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        System.out.println(">>Проверка создания карты из тестового файла<<");

        GameIO gameIO = (GameIO) ReflectionTestUtils.getClass("magic." + userSubfolder, "GameIOImpl").getDeclaredConstructor().newInstance();

        InputStream resourceAsStream = getClass().getResourceAsStream("/test-card.txt");

        if (Objects.isNull(resourceAsStream)) {
            Assertions.fail("Не найден файл test-card.txt");
        }

        CreatureCard creatureCard = gameIO.cardLoad("src/test/resources/test-card.txt");

        Assertions.assertNotNull(creatureCard, "❌ Загрузка карты возвращает null");

        Assertions.assertEquals("D'Avenant Healer", creatureCard.getName());

        System.out.println("✅ Карта загружена корректно");
    }

    @Test
    @Order(3)
    @DisplayName("Тестирование контекста битвы")
    void battleContextTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        BattleContext battleContext = (BattleContext) ReflectionTestUtils.getClass("magic." + userSubfolder, "BattleContextImpl").getDeclaredConstructor().newInstance();

        GameIO gameIO = (GameIO) ReflectionTestUtils.getClass("magic." + userSubfolder, "GameIOImpl").getDeclaredConstructor().newInstance();

        CreatureCard keenCard = gameIO.cardLoad("src/test/resources/keen.txt");

        CreatureCard davenantCard = gameIO.cardLoad("src/test/resources/test-card.txt");

        System.out.println(">>Проверка фазы нанесения урона<<");

        System.out.printf("""
                        Карты до нанесения урона
                        %s: %d/%d
                        %s: %d/%d
                        %n""", keenCard.getName(), keenCard.getPower(), keenCard.getToughness(),
                davenantCard.getName(), davenantCard.getPower(), davenantCard.getToughness());

        battleContext.dealDamagePhase(keenCard, davenantCard);

        System.out.printf("""
                        Карты после нанесения урона
                        %s: %d/%d
                        %s: %d/%d
                        %n""", keenCard.getName(), keenCard.getPower(), keenCard.getToughness(),
                davenantCard.getName(), davenantCard.getPower(), davenantCard.getToughness());

        Assertions.assertTrue(davenantCard.getToughness() <= 0);

        System.out.println("✅ Тест фазы пройден");

        System.out.println(">>Проверка фазы блокирования<<");

        System.out.printf("""
                        Карты до блокирования
                        %s: %d/%d
                        %s: %d/%d
                        %n""", keenCard.getName(), keenCard.getPower(), keenCard.getToughness(),
                davenantCard.getName(), davenantCard.getPower(), davenantCard.getToughness());

        battleContext.blockDamagePhase(davenantCard, keenCard);

        System.out.printf("""
                        Карты после блокирования
                        %s: %d/%d
                        %s: %d/%d
                        %n""", keenCard.getName(), keenCard.getPower(), keenCard.getToughness(),
                davenantCard.getName(), davenantCard.getPower(), davenantCard.getToughness());

        Assertions.assertTrue(keenCard.getToughness() <= 0);

        System.out.println("✅ Тест фазы пройден");

        System.out.println(">>Проверка фазы оценки здоровья<<");

        System.out.printf("""
                        Состояние карт
                        %s: %d/%d
                        %s: %d/%d
                        %n""", keenCard.getName(), keenCard.getPower(), keenCard.getToughness(),
                davenantCard.getName(), davenantCard.getPower(), davenantCard.getToughness());

        CreatureCard winnerCard = battleContext.checkToughnessPhase(List.of(keenCard, davenantCard));

        Assertions.assertNull(winnerCard);

        System.out.println("✅ Тест фазы пройден");
    }
}
