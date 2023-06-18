package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Booking;

public class BookingDAO {

	final private Connection con;
	
	public BookingDAO(Connection con) {
		this.con = con;
	}
	
	public boolean create(Booking booking) {
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO reservas "
					+ "(fecha_entrada, fecha_salida, valor, forma_pago)"
					+ "VALUES (?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			try (statement) {
				statement.setDate(1, booking.getDateIn());
				statement.setDate(2, booking.getDateOut());
				statement.setDouble(3,booking.getPrice());
				statement.setString(4, booking.getMethodOfPayment());
				
				statement.execute();
				
				final ResultSet resultSet = statement.getGeneratedKeys();
				
				while (resultSet.next()) {
					booking.setId(resultSet.getInt(1));
				}
			}
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Booking> read() {
		List<Booking> listOfBooking = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT id, fecha_entrada, fecha_salida, valor, forma_pago"
					+ " FROM reservas");
			
			try (statement) {
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				
				try (resultSet) {
					while (resultSet.next()) {
						Booking row = new Booking(
								resultSet.getInt("id"),
								resultSet.getDate("fecha_entrada"),
								resultSet.getDate("fecha_salida"),
								resultSet.getDouble("valor"),
								resultSet.getString("forma_pago")
								);
						listOfBooking.add(row);
					}
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listOfBooking;
	}

	public int update(Booking booking) {
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE reservas "
					+ "SET "
					+ "fecha_entrada = ?, "
					+ "fecha_salida = ?, "
					+ "valor = ?, "
					+ "forma_pago = ? "
					+ "WHERE id = ?");
			
			try (statement) {
				statement.setDate(1, booking.getDateIn());
				statement.setDate(2, booking.getDateOut());
				statement.setDouble(3, booking.getPrice());
				statement.setString(4, booking.getMethodOfPayment());
				statement.setInt(5, booking.getId());
				
				statement.execute();
				
				return statement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int delete(Integer id) {
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"DELETE FROM reservas "
					+ "WHERE id = ?");
			
			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				return statement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
