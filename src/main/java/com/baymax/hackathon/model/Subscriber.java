package com.baymax.hackathon.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created by npanthi on 11/11/2017.
 */
@Entity
public class Subscriber implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscriber_id")
    private long subscriberId;
    @OneToMany
    private List<Transaction> transactions;
	public long getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(long subscriberId) {
		this.subscriberId = subscriberId;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
