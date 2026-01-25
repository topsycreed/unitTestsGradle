package database.hibernate.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "zoo_animal")
public class ZooAnimal {
    @EmbeddedId
    private ZooAnimalId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("zooId")
    @JoinColumn(name = "zoo_id", referencedColumnName = "id")
    private Zoo zoo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("animalId")
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private Animal animal;

    @Column(name = "time_apperance")
    private LocalDateTime timeApperance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workman", referencedColumnName = "id")
    private Workman workman;

    public ZooAnimalId getId() {
        return id;
    }

    public void setId(ZooAnimalId id) {
        this.id = id;
    }

    public Zoo getZoo() {
        return zoo;
    }

    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDateTime getTimeApperance() {
        return timeApperance;
    }

    public void setTimeApperance(LocalDateTime timeApperance) {
        this.timeApperance = timeApperance;
    }

    public Workman getWorkman() {
        return workman;
    }

    public void setWorkman(Workman workman) {
        this.workman = workman;
    }
}
