package ru.job4j.cars.models;

import javax.persistence.*;

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
}
