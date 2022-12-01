package net.javaguides.springboot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;


	@Column(name = "creation_date")
	private LocalDateTime date;

	@Column(name = "who_created")
	private String user;

	@Column(name = "who_changed")
	private String userChangeBy;

	@Column(name = "change_date")
	private LocalDateTime changedate;

	@ManyToOne(cascade = {CascadeType.ALL})
    PaymentTransfer paymentTransfer;

	@OneToOne(cascade = {CascadeType.ALL})
	Receiver receiver;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserChangeBy() {
		return userChangeBy;
	}

	public void setUserChangeBy(String userChangeBy) {
		this.userChangeBy = userChangeBy;
	}

	public LocalDateTime getChangedate() {
		return changedate;
	}

	public void setChangedate(LocalDateTime changedate) {
		this.changedate = changedate;
	}

	public PaymentTransfer getPaymentTransfer() {
		return paymentTransfer;
	}

	public void setPaymentTransfer(PaymentTransfer paymentTransfer) {
		this.paymentTransfer = paymentTransfer;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
}
