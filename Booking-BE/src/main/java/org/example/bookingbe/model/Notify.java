package org.example.bookingbe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_notify", columnDefinition = "DATETIME")
    private LocalDateTime dateNotify;

    public Notify() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateNotify() {
        return dateNotify;
    }

    public void setDateNotify(LocalDateTime dateNotify) {
        this.dateNotify = dateNotify;
    }
}
