package org.example.bookingbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "room_type")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_name", columnDefinition = "varchar(50)")
    private String typeName;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "description", columnDefinition = "varchar(100)")
    private String description;

    public RoomType() {
    }

    public RoomType(Long id, String typeName, Integer capacity, String description) {
        this.id = id;
        this.typeName = typeName;
        this.capacity = capacity;
        this.description = description;
    }

    public RoomType(Long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
