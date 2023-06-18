package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexion {
	
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/alura_hotel?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"fgpAL707180#"
				);
		
		System.out.println("Cerrando la conexion...");
		con.close();
	}

}
