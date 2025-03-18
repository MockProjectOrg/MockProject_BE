package org.example.bookingbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status_name", columnDefinition = "varchar(50)")
    private String statusName;
    @Column(name = "status_code", unique = true, nullable = false)
    private String statusCode;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Status() {

    }

    public Status(Long id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
