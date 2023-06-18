package controller;

import java.util.List;

import dao.BookingDAO;
import factory.ConnectionFactory;
import model.Boarders;
import model.Booking;

public class BookingController {
	
	private ConnectionFactory connectionFactory;
	private BookingDAO bookingDAO;
	
	public BookingController() {
		connectionFactory = new ConnectionFactory();
		bookingDAO = new BookingDAO(connectionFactory.getConnection());
	}

	public boolean create(Booking booking) {
		return bookingDAO.create(booking);
	}
	
	public List<Booking> read() {
		return bookingDAO.read();
	}
	
	public int update(Booking booking) {
		return bookingDAO.update(booking);
	}
	
	public int delete(Integer id) {
		return bookingDAO.delete(id);
	}
}
