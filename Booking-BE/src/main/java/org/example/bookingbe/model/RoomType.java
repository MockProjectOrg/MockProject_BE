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

    public RoomType() {
    }

    public RoomType(Long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
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
