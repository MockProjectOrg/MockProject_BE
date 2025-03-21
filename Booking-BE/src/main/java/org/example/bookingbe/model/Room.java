package org.example.bookingbe.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price")
    private Double price;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToMany
    @JoinTable(
            name = "room_amenity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "utilities_id")
    )
    private Set<Utilities> utilities = new HashSet<>();
    public Room() {
    }

    public Room(Long id, Double price, String description, RoomType roomType, Hotel hotel, Status status) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.roomType = roomType;
        this.hotel = hotel;
        this.status = status;
    }

    public Room(Long id, Double price, String roomName, String description, RoomType roomType, Status status, Hotel hotel, Set<Utilities> utilities) {
        this.id = id;
        this.price = price;
        this.roomName = roomName;
        this.description = description;
        this.roomType = roomType;
        this.status = status;
        this.hotel = hotel;
        this.utilities = utilities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Set<Utilities> getUtilities() {
        return utilities;
    }

    public void setUtilities(Set<Utilities> utilities) {
        this.utilities = utilities;
    }
}
