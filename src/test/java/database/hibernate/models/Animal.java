package database.hibernate.models;

import jakarta.persistence.*;

@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "`name`")
    private String name;
    @Column(name = "age")
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`type`", referencedColumnName = "id")
    private AnimalType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sex", referencedColumnName = "id")
    private Sex sex;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place", referencedColumnName = "id")
    private Places place;

    public Animal() {
    }

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

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Places getPlace() {
        return place;
    }

    public void setPlace(Places place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", type=" + type +
                ", sex=" + sex +
                ", place=" + place +
                '}';
    }
}
