package ru.mirea.magic.gamelogic;

import ru.mirea.magic.card.*;

import java.util.List;

/**
 * Контекст хранит в себе карты существ игроков, участвующих в матче.
 * Методы отвечают за каждую из трёх фаз в цикле боя соответственно:
 * <ol>
 *  <li>Фаза атаки - выбирается атакующая карта и наносит урон другой</li>
 *  <li>Фаза блокирования - получившая урон карта наносит урон атакующей</li>
 *  <li>Фаза проверки здоровья - выбирается победившая карта</li>
 * </ol>
 */
public interface BattleContext {

    /**
     * Фаза нанесения урона, атакующее существо <code>(attacker)</code> вызывает метод атаки и передаёт туда
     * цель <code>(target)</code>.
     * @param attacker атакующая в этой фазе карта
     * @param target получающая в этой фазе урон карта
     */
    void dealDamagePhase(Attacker attacker, Damagable target);

    /**
     * Фаза блокирования урона от атакуемой карты.
     * Атакуемое на прошлой фазе существо становится блокирующим и наносит урон атакующему существу.
     * @param blocker блокирующая в этой фазе карта
     * @param target атакующая в прошлой фазе карта
     */
    void blockDamagePhase(Blocker blocker, CreatureCard target);

    /**
     * Проверка здоровья карт, если у одной из карт показатель toughness <= 0, то возвращается уцелевшее существо. Если оба существа живы, либо оба мертвы, то возвращается null.
     * @param cardList список карт
     * @return победившее существо, либо null
     */
    CreatureCard checkToughnessPhase(List<CreatureCard> cardList);

}
