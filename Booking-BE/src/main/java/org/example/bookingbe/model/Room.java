package org.example.bookingbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price")
    private Double price;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    
    @OneToMany (@mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    private List<Image> images;
    private Image image;
    @JoinColumn(name = "status_id")
    private Status status;
    
    public Room() {}

    public Room(Long id, Double price, String description, RoomType roomType, Hotel hotel,Image image, Status status) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.roomType = roomType;
        this.hotel = hotel;
        this.image = image;
        this.status = status;
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
    
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    public Image getImage(){
      return image;
    }
    public void setImage(Image image){
      this.image = image;
    }
    public Status getStatus(){
      return status;
    }
    public void setStatus(Status status){
      this.status = status;
    }
}
