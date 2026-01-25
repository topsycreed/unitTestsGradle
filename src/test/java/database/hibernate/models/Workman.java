package database.hibernate.models;

import jakarta.persistence.*;

@Entity
@Table(name = "workman")
public class Workman {
    @Id
    int id;
    @Column(name = "`name`", nullable = false)
    String name;
    @Column(name = "age")
    int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`position`", referencedColumnName = "id")
    Position position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Workman{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", position=" + position +
                '}';
    }
}
