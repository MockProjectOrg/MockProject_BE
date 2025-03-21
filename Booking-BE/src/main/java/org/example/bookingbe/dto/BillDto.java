package org.example.bookingbe.dto;

public class BillDto {
    private String lastName;
    private String firstName;
    private String address;
    private String email;
    private String phone;
    private Double price;

    public BillDto() {
    }

    public BillDto(Double price) {
        this.price = price;
    }

    public BillDto(String lastName, String firstName, String address, String email, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
