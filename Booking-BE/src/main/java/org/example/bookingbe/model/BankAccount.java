package org.example.bookingbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bank_name", columnDefinition = "varchar(100)")
    private String bankName;
    @Column(name = "account_number", columnDefinition = "varchar(50)")
    private String accountNumber;
    @Column(name = "account_holder", columnDefinition = "varchar(100)")
    private String accountHolder;
    @Column(name = "branch", columnDefinition = "varchar(150)")
    private String branch;
    @Column(name = "swift_code",columnDefinition = "varchar(20)")
    private String swiftCode;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public BankAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
