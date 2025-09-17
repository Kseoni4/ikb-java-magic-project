package ru.mirea.ksenofontov.magic.card;

import java.util.List;
import java.util.Objects;

public class CreatureCard extends Card implements HasAbility, Attacker, Blocker, DamageDealer, Damagable {
    private int power;

    private int toughness;

    private final List<Ability> abilities;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreatureCard that = (CreatureCard) o;
        return that.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, toughness, abilities);
    }

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
        System.out.printf("%s атакует %s%n", this.getName(), target.getName());
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
