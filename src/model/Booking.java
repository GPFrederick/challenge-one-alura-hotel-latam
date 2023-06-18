package model;

import java.sql.Date;

public class Booking {
	
	private int id;
	private Date dateIn;
	private Date dateOut;
	private double price;
	private String methodOfPayment;
	
	public Booking() {}
	
	public Booking(int id, Date dateIn, Date dateOut, double price, String methodOfPayment) {
		this.id = id;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.price  = price;
		this.methodOfPayment = methodOfPayment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(String methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}
	/*
	@Override
	public String toString() {
		return String.format(
				"{id: %d, dateIn: %t, dateOut: %t, price: %s, methodOfPayment: %s}",
				this.id,
				this.dateIn,
				this.dateOut,
				this,price,
				this.methodOfPayment
				);
	}
	*/

	@Override
	public String toString() {
		return "Booking [id=" + id + ", dateIn=" + dateIn + ", dateOut=" + dateOut + ", price=" + price
				+ ", methodOfPayment=" + methodOfPayment + "]";
	}
	
}
