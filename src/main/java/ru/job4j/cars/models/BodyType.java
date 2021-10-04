package ru.job4j.cars.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "body_types")
public class BodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "body_type")
    private String name;

    public static BodyType of(String name) {
        BodyType bodyType = new BodyType();
        bodyType.setName(name);
        return bodyType;
    }

    public BodyType() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyType bodyType = (BodyType) o;
        return id == bodyType.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
