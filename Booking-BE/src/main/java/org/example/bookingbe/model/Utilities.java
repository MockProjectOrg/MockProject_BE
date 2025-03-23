package org.example.bookingbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "utilities")
public class Utilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public Utilities(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Utilities() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
