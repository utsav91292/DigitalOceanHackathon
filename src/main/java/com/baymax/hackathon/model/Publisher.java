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
public class Publisher implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publisher_id")
    private long publisherId;
    @OneToMany
    private List<Transaction> transactions;
	public long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
