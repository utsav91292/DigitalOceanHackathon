package com.baymax.hackathon.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by npanthi on 11/11/2017.
 */
@Entity
public class Transaction {
    @Id
    @Column(name="transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @Column(name="transaction_date")
    private Date transactionDate;
    @Column(name="remarks")
    private String remarks;
    @JoinColumn(name="booking_id", referencedColumnName = "booking_id")
    private Booking booking;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
