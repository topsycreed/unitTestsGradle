package database.hibernate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ZooAnimalId implements Serializable {
    @Column(name = "zoo_id")
    private int zooId;

    @Column(name = "animal_id")
    private int animalId;

    public ZooAnimalId() {
    }

    public int getZooId() {
        return zooId;
    }

    public void setZooId(int zooId) {
        this.zooId = zooId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ZooAnimalId that = (ZooAnimalId) o;
        return zooId == that.zooId && animalId == that.animalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zooId, animalId);
    }
}
