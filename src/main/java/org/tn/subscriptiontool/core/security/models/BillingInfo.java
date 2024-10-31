package org.tn.subscriptiontool.core.security.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BILLING_INFO")
public class BillingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_info_id")
    private int billingInfoId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    // Default constructor
    public BillingInfo() {}

    // Parameterized constructor
    public BillingInfo(int billingInfoId, User user, String paymentMethod, String cardNumber, Date expirationDate) {
        this.billingInfoId = billingInfoId;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public int getBillingInfoId() {
        return billingInfoId;
    }

    public void setBillingInfoId(int billingInfoId) {
        this.billingInfoId = billingInfoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "BillingInfo{" +
                "billingInfoId=" + billingInfoId +
                ", user=" + user +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}