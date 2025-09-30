package ru.mirea.magic.card;

import java.util.List;
import java.util.Map;

public class CreatureCard extends Card implements HasAbility, Attacker, Blocker, DamageDealer, Damagable {

    private int power;

    private int toughness;

    private final Map<Class<? extends Ability>, Ability> abilities;

    public CreatureCard(String name,
                        ManaCost manaCost,
                        CardType cardType,
                        String setTitle,
                        String oracleText,
                        int power,
                        int toughness,
                        Map<Class<? extends Ability>, Ability> abilities) {
        super(name, manaCost, cardType, setTitle, oracleText);
        this.power = power;
        this.toughness = toughness;
        this.abilities = abilities;
    }

    public int getPower() {
        return power;
    }

    public int getToughness() {
        return toughness;
    }

    public void changeToughness(int amount){
        this.toughness += amount;
    }

    @Override
    public String toString() {
        return "CreatureCard{" +
                "power=" + power +
                ", toughness=" + toughness +
                ", abilities=" + abilities +
                "} " + super.toString();
    }

    @Override
    public void attack(CreatureCard target) {
        this.dealDamage(this.power, target);
    }

    @Override
    public void block(CreatureCard attacker) {
        this.dealDamage(power, attacker);
    }

    @Override
    public void takeDamage(int value) {
        this.toughness -= value;
    }

    @Override
    public void dealDamage(int amount, Damagable target) {
        target.takeDamage(amount);
    }

    @Override
    public List<Ability> getAbilities() {
        return this.abilities.values().stream().toList();
    }

    @Override
    public void addAbility(Ability ability) {
        this.abilities.put(ability.getClass(), ability);
    }
}
