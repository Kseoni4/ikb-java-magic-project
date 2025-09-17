package ru.mirea.magic.card;

public class Student {
    private final String lastName;

    private final String firstName;

    private final String group;

    private Card card;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGroup() {
        return group;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Student(String lastName, String firstName, String group, Card card) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.group = group;
        this.card = card;
    }

    @Override
    public String toString() {
        return "Student{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", group='" + group + '\'' +
                ", card=" + card +
                '}';
    }
}
