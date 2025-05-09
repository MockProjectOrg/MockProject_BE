package org.example.bookingbe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.bookingbe.custom.annotationValidate.AgeOver18;

import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar", columnDefinition = "varchar(255)")
    private String avatar;

    @Column(name = "user_name", columnDefinition = "varchar(100)", unique = true)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d._-]+$", message = "example: user_123 or user123")
    private String userName;

    @Column(name = "email", columnDefinition = "varchar(50)")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    private String email;

    @Column(name = "phone", columnDefinition = "varchar(10)")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number up to 10 digits")
    private String phone;
    @Column(name = "password", columnDefinition = "TEXT")
    private String password;
    @Column(name = "address", columnDefinition = "varchar(155)")
    private String address;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "first_name", columnDefinition = "varchar(50)")
    @Size(min = 2, max = 10, message = "First name must be between 2 and 10 characters")
    private String firstName;
    @Column(name = "last_name", columnDefinition = "varchar(50)")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @Column(name = "birthday", columnDefinition = "DATE")
    @AgeOver18
    private LocalDate birthday;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User(Long userId) {}

    public User(Long id, String userName, String email, String phone, String password, String address, Role role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public User() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    // Thêm vào class User
    @Column(name = "created_at")
    private LocalDate createdAt;

    // Thêm getter và setter
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

}
