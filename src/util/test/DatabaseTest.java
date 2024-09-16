package util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import util.Database;

class DatabaseTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception { Database.LoadDriver(); }

	@Test
	void testDbAccess() throws IOException, SQLException {
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Database.Connection();
			rs = con.createStatement().executeQuery("SELECT nombre, descripcion, stock FROM material;");
			
			int i = 0;
			while (rs.next()) {
				if(i == 0)
					assertEquals("Botella de agua 1L", rs.getString("nombre"));
				if(i == 2)
					assertEquals("Abrigo", rs.getString("nombre"));
				i++;
			}
			assertEquals(3,	rs.getMetaData().getColumnCount());
			assertEquals(3, i);
		}
		catch (SQLException e) { throw e; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testBolean2Sql() {
		assertEquals(1, Database.Boolean2Sql(true));
		assertEquals(0, Database.Boolean2Sql(false));
	}
	
	@Test
	void testString2Sql() {
		assertEquals("hola", 			Database.String2Sql("hola", false, false));
		assertEquals("'hola'", 			Database.String2Sql("hola", true, false));
		assertEquals("%hola%", 			Database.String2Sql("hola", false, true));
		assertEquals("'%hola%'", 		Database.String2Sql("hola", true, true));
		assertEquals("O''Connell", 		Database.String2Sql("O'Connell", false, false));
		assertEquals("'O''Connell'", 	Database.String2Sql("O'Connell", true, false));
		assertEquals("%''Smith ''%", 	Database.String2Sql("'Smith '", false, true));
		assertEquals("'''Smith '''", 	Database.String2Sql("'Smith '", true, false));
		assertEquals("'%''Smith ''%'", 	Database.String2Sql("'Smith '", true, true));
	}
}