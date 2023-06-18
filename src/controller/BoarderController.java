package controller;

import java.util.List;

import dao.BoarderDAO;
import factory.ConnectionFactory;
import model.Boarders;

public class BoarderController {
	
	private ConnectionFactory connectionFactory;
	private BoarderDAO boarderDAO;
	
	public BoarderController() {
		connectionFactory = new ConnectionFactory();
		boarderDAO = new BoarderDAO(connectionFactory.getConnection());
	}
	
	public boolean create(Boarders boarder) {
    	return boarderDAO.create(boarder);
    }
	
	public List<Boarders> read() {
		return boarderDAO.read();
	}
	
	public int update(Boarders boarder) {
		return boarderDAO.update(boarder);
	}
	
	public int delete(Integer boarderId, Integer bookingId) {
		return boarderDAO.delete(boarderId, bookingId);
	}
	
	/*
	public int getBookingID(Boarders boarders) {
		return boarderDAO.getLastBookingID(boarders);
	}
	*/
}
