package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Boarders;

public class BoarderDAO {

	final private Connection con;
	
	public BoarderDAO(Connection con) {
		this.con = con;
	}
	
	public boolean create(Boarders boarder) {
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO huespedes (nombre, apellido, fecha_de_nacimiento, nacionalidad, telefono, id_reserva)"
					+ "VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, boarder.getName());
			statement.setString(2, boarder.getLastName());
			statement.setDate(3, boarder.getDateOfBirth());
			statement.setString(4, boarder.getCitizenship());
			statement.setString(5, boarder.getPhoneNumber());
			statement.setInt(6, boarder.getIdBooking());
			
			statement.execute();
			
			final ResultSet resultSet = statement.getGeneratedKeys();
			
			while (resultSet.next()) {
				boarder.setId(resultSet.getInt(1));
			}
			return true;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Boarders> read() {
		List<Boarders> listOfBoarders = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT id, nombre, apellido, fecha_de_nacimiento, nacionalidad, telefono, id_reserva "
					+ "FROM huespedes");
			
			try (statement) {
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				
				try (resultSet) {
					while (resultSet.next()) {
						Boarders row =  new Boarders(
								resultSet.getInt("id"),
								resultSet.getString("nombre"),
								resultSet.getString("apellido"),
								resultSet.getDate("fecha_de_nacimiento"),
								resultSet.getString("nacionalidad"),
								resultSet.getString("telefono"),
								resultSet.getInt("id_reserva")
								);
						listOfBoarders.add(row);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listOfBoarders;
	}
	
	public int update(Boarders boarder) {
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE huespedes "
					+ "SET "
					+ "nombre = ?, "
					+ "apellido = ?, "
					+ "fecha_de_nacimiento = ?, "
					+ "nacionalidad = ?, "
					+ "telefono = ?, "
					+ "id_reserva = ? "
					+ "WHERE id = ?");
			
			try (statement) {
				statement.setString(1, boarder.getName());
				statement.setString(2, boarder.getLastName());
				statement.setDate(3, boarder.getDateOfBirth());
				statement.setString(4, boarder.getCitizenship());
				statement.setString(5, boarder.getPhoneNumber());
				statement.setInt(6, boarder.getIdBooking());
				statement.setInt(7, boarder.getId());
				
				statement.execute();
				
				return statement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int delete(Integer boarderId, Integer bookingId) {
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"DELETE FROM huespedes "
					+ "WHERE id = ?");
			final PreparedStatement statement2 = con.prepareStatement(
					"DELETE FROM reservas "
					+ "WHERE id = ?");
			
			try (statement) {
				statement.setInt(1, boarderId);
				statement2.setInt(1, bookingId);
				statement.execute();
				statement2.execute();
				
				return statement.getUpdateCount() + statement2.getUpdateCount();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	public int getLastBookingID(Boarders boarders) {
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT id_reserva "
					+ "FROM huespedes "
					+ "ORDER BY id_reserva DESC "
					+ "LIMIT 1");
			
			statement.execute();
			
			final ResultSet resultSet = statement.getResultSet();
			
			if (resultSet.next()) {
				boarders.setIdBooking(resultSet.getInt("id_reserva") + 1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return boarders.getIdBooking();
	}
	*/
}
