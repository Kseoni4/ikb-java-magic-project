package ru.mirea.hidden.tests;

import org.junit.jupiter.api.*;

import java.io.File;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

@Tag("structure-main-tests")
@DisplayName("Тест UML структуры")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StructureClassTest {

    private static String userSubfolder;

    private static final Map<String, Class<?>> classes = new HashMap<>();

    private static final String[] requiredClasses = new String[]{"Card", "CreatureCard", "CardType",
            "ManaCost", "ManaType", "Student",
            "SuperType", "Ability", "HasAbility",
            "Attacker", "Blocker", "DamageDealer", "Damagable"};

    private static final String[] requiredImplementations = new String[]{"HasAbility",
            "Attacker", "Blocker", "DamageDealer", "Damagable"};

    private static final Map<String, String[]> requiredClassFieldsMap = new HashMap<>(Map.of(
            "Card", new String[]{"name", "manaCost", "cardType", "setTitle", "oracleText"},
            "CreatureCard", new String[]{"power", "toughness", "abilities"},
            "CardType", new String[]{"superType", "type", "subType"},
            "ManaCost", new String[]{"genericMana", "coloredMana"},
            "Student", new String[]{"lastName", "firstName", "group", "card"}
    ));

    private static Map<String, Map<String, Class<?>[]>> requiredImplMethods;

    private final Map<String, String[]> requiredEnumConstantsMap = new HashMap<>(Map.of(
            "ManaType", new String[]{"WHITE", "BLUE", "BLACK", "RED", "GREEN", "COLORLESS"},
            "SuperType", new String[]{"NONE", "LEGENDARY", "BASIC", "ELITE", "SNOW", "TOKEN", "WORLD"}
    ));

    private final Map<String, Integer> requiredFieldsCount = requiredClassFieldsMap.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, item -> item.getValue().length));

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

        requiredImplMethods = new HashMap<String, Map<String, Class<?>[]>>(Map.of(
                "CreatureCard", Map.of(
                        "getAbilities", new Class[]{},
                        "addAbility", new Class[]{ReflectionTestUtils.getClass("magic.card", "Ability")},
                        "attack", new Class[]{ReflectionTestUtils.getClass("magic.card", "CreatureCard")},
                        "block", new Class[]{ReflectionTestUtils.getClass("magic.card", "CreatureCard")},
                        "dealDamage", new Class[]{int.class, ReflectionTestUtils.getClass("magic.card", "Damagable")},
                        "takeDamage", new Class[]{int.class}
                )
        ));
    }

    @Test
    @Order(1)
    @DisplayName("1 Проверка наличия требуемых классов")
    void requiredClassesIsExists() {
        System.out.println(">>Проверка наличия требуемых классов<<");
        for (String requiredClass : requiredClasses) {
            try {
                Class<?> aClass = ReflectionTestUtils.getClass("magic.card", requiredClass);
                classes.put(requiredClass, aClass);
                System.out.printf("Class '%s' is exist ✅%n", requiredClass);
            } catch (AssertionError e){
                Assertions.fail(e.getMessage());
            }
        }
    }

    @Test
    @Order(2)
    @DisplayName("2 Проверка корректности структуры")
    void classStructureIsCorrect() {
        System.out.println(">>Проверка корректности структуры<<");

        checkAbstractClass("Card");
        checkClassInheritance("CreatureCard", "Card");

        System.out.println(">>Проверка реализации интерфейсов<<");
        for (String requiredImplementation : requiredImplementations) {
            checkInterfaceImplementation("CreatureCard", requiredImplementation);
        }

        System.out.println(">>Проверка реализации методов<<");
        for (String className : requiredImplMethods.keySet()) {
            Map<String, Class<?>[]> stringMap = requiredImplMethods.get(className);

            for (String methodName : stringMap.keySet()) {
                Class<?>[] params = stringMap.get(methodName);

                checkMethodExists(className, methodName, params);
            }
        }

    }

    @Test
    @Order(3)
    @DisplayName("3 Проверка содержания классов")
    void classInsideIsCorrect() {
        System.out.println(">>Проверка содержания классов<<");

        checkFields();

        checkClassFieldsCount();

        checkEnumConstantsExists();

    }


    private void checkFields(){
        System.out.println(">>Проверка наличия полей<<");
        requiredClassFieldsMap.forEach((className, fields) -> {
            for (String field : fields) {
                Assertions.assertDoesNotThrow(() -> {
                            checkFieldExists(className, field);
                        }, "❌ Ошибка, отсутствует поле %s в классе %s".formatted(field, className)
                );

            }
            System.out.printf("Требуемые поля в классе %s присутствуют ✅%n", className);
        });
    }

    private void checkAbstractClass(String className) {
        System.out.println(">>Проверка абстрактности класса<<");
        Class<?> clazz = classes.get(className);
        if (clazz == null || !Modifier.isAbstract(clazz.getModifiers())) {
            Assertions.fail("❌ Класс " + className + " должен быть абстрактным");
        } else {
            System.out.printf("Класс %s абстрактный ✅%n", className);
        }
    }

    private void checkClassInheritance(String className, String parentClassName) {
        System.out.println(">>Проверка наследования класса<<");
        Class<?> clazz = classes.get(className);
        Class<?> parentClass = classes.get(parentClassName);

        if (clazz != null && parentClass != null && !parentClass.isAssignableFrom(clazz)) {
            Assertions.fail("❌ Класс " + className + " должен наследовать " + parentClassName);
        } else {
            System.out.printf("Класс %s наследует %s ✅%n", className, parentClassName);
        }
    }

    private void checkInterfaceImplementation(String className, String interfaceName) {
        Class<?> clazz = classes.get(className);
        Class<?> interfaceClass = classes.get(interfaceName);

        if (clazz != null && interfaceClass != null) {
            boolean implementsInterface = Arrays.asList(clazz.getInterfaces()).contains(interfaceClass);
            if (!implementsInterface) {
                Assertions.fail("❌ Класс " + className + " должен реализовывать интерфейс " + interfaceName);
            } else {
                System.out.printf("Класс %s реализует интерфейс %s ✅%n", className, interfaceName);
            }
        }
    }

    private void checkFieldExists(String className, String fieldName) {
        Class<?> clazz = classes.get(className);
        if (clazz != null) {
            try {
                Field field = ReflectionTestUtils.getField(clazz, fieldName);
                System.out.printf("✅ Поле %s.%s существует%n", className, fieldName);
            } catch (AssertionError e) {
                Assertions.fail(e.getMessage());
            }
        } else {
            Assertions.fail("❌ Класс '%s' не найден".formatted(className));
        }
    }

    private void checkMethodExists(String className, String methodName, Class<?>... parameterTypes) {
        Class<?> clazz = classes.get(className);
        if (clazz != null) {
            try {
                ReflectionTestUtils.getMethod(clazz, methodName, parameterTypes);
                System.out.printf("✅ Метод %s.%s(%s) существует%n", className, methodName, Arrays.stream(parameterTypes).map(Class::getSimpleName).toList());
            } catch (AssertionError e) {
                Assertions.fail(e.getMessage());
            }
        }
    }

    private void checkEnumConstantsExists(){
        System.out.println(">>Проверка наличия enum констант<<");
        requiredEnumConstantsMap.forEach((enumType, enumConstants) -> {
            Class<?> aClass = classes.get(enumType);
            Object[] constants = aClass.getEnumConstants();

            List<String> valueNames = Arrays.stream(constants).map(o -> ((Enum<?>) o).name()).toList();

            System.out.println(valueNames);

            for (String enumConstant : enumConstants) {
                Assertions.assertTrue(valueNames.contains(enumConstant), "❌ %s должен содержать %s".formatted(enumType, enumConstant));
            }
            System.out.printf("%s содержит все константы ✅%n", enumType);
        });
    }

    private void checkClassFieldsCount(){
        System.out.println(">>Проверка количества полей<<");
        requiredFieldsCount.forEach((className, count) -> {
            Class<?> aClass = classes.get(className);
            Assertions.assertEquals(aClass.getDeclaredFields().length, count, "❌ %s должен содержать ровно %s полей".formatted(className, count));
            System.out.printf("%s содержит ровно %s полей ✅%n", className, count);
        });
    }

    private void testCardCreation() {
        Class<?> manaCostClass = classes.get("ManaCost");
        Class<?> cardClass = classes.get("Card");

        if (manaCostClass != null && cardClass != null) {
            try {
                Object manaCost = ReflectionTestUtils.createInstance(manaCostClass, 3);
                ReflectionTestUtils.createInstance(cardClass, "Test Card", manaCost);
                Assertions.fail("❌ Не удалось предотвратить создание экземпляра абстрактного класса Card");
            } catch (AssertionError e) {
                System.out.println("Создание экземпляра абстрактного класса Card предотвращено ✅");
            }
        }
    }

    Method getAnswerMethodForTask(String taskName, Class<?>... arguments) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<?> taskClass = Class.forName("ru.mirea.%s.%s".formatted(userSubfolder, taskName));
        return taskClass.getDeclaredMethod("answer", arguments);
    }

    Object getClassTaskObject(String taskName) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> taskClass = Class.forName("ru.mirea.%s.%s".formatted(userSubfolder, taskName));
        return taskClass.getDeclaredConstructor().newInstance();
    }
}
