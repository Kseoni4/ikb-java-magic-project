package ru.mirea.magic.card;

import java.util.List;

public class CreatureCard extends Card implements HasAbility, Attacker, Blocker, DamageDealer, Damagable {

    private int power;

    private int toughness;

    private final List<Ability> abilities;

    public CreatureCard(String name,
                        ManaCost manaCost,
                        CardType cardType,
                        String setTitle,
                        String oracleText,
                        int power,
                        int toughness,
                        List<Ability> abilities) {
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
        return List.of();
    }

    @Override
    public void addAbility(Ability ability) {

    }
}
