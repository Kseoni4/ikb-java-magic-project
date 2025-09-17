package ru.mirea.hidden.tests;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import ru.mirea.magic.*;
import ru.mirea.magic.card.CreatureCard;
import ru.mirea.magic.gamelogic.BattleSystem;
import ru.mirea.magic.gamelogic.GameEngine;

@Tag("game-logic-tests")
@DisplayName("Тестирование игрового функционала")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameLogicTest {

    private static String userSubfolder;

    private final String[] requiredImplements = new String[]{"GameEngineImpl", "BattleSystemImpl", "BattleContextImpl"};

    @BeforeAll
    @DisplayName("Получаем пользовательский пакет внутри ru.mirea")
    static void getUserPackageSubfolder() {
        System.out.println(">>Получение пользовательского пакета внутри ru.mirea<<");

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

        for (String className : requiredImplements) {
            Assertions.assertDoesNotThrow(() -> {
                ReflectionTestUtils.getClass("magic." + userSubfolder, className).getDeclaredConstructor().newInstance();
                System.out.printf("✅Реализация %s найдена%n", className);
            }, "❌ Реализация %s отсутствует".formatted(className));
        }
    }

    @Test
    @Order(2)
    @DisplayName("Проверка создания карты из тестового файла")
    void createCardTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, FileNotFoundException {
        System.out.println(">>Проверка создания карты из тестового файла<<");

        GameEngine gameEngineImpl = (GameEngine) ReflectionTestUtils.getClass("magic." + userSubfolder, "GameEngineImpl").getDeclaredConstructor().newInstance();

        InputStream resourceAsStream = getClass().getResourceAsStream("/test-card.txt");

        if (Objects.isNull(resourceAsStream)) {
            Assertions.fail("Не найден файл test-card.txt");
        }

        CreatureCard creatureCard = gameEngineImpl.cardLoad("src/test/resources/test-card.txt");

        Assertions.assertNotNull(creatureCard, "❌ Загрузка карты возвращает null");

        Assertions.assertEquals("D'Avenant Healer", creatureCard.getName());
    }
}
