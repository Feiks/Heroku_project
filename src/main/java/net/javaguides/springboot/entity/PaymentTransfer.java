package net.javaguides.springboot.entity;

import javax.persistence.*;
@Entity
@Table(name = "paymentTransfer")
public class PaymentTransfer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "amount")
    private int amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "code")
    private String code;

    @Column(name = "state")
    private String state;
    //добавить

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
